#!/usr/bin/env python3
"""
Cleans expected YAML files by removing dynamic fields that change between runs.
Usage: python scripts/clean_expected.py expected/*.yaml
"""

import re
import sys
import yaml


DYNAMIC_RESULT_KEYS = {"message", "stacktrace", "muted"}
DYNAMIC_EXECUTION_KEYS = {"start_time", "end_time", "duration", "thread", "stacktrace"}
EMPTY_DEFAULTS = {
    "fields": {},
    "attachments": [],
    "params": {},
    "param_groups": [],
    "steps": [],
}


def clean_attachment(att):
    """Keep only file_name and mime_type."""
    cleaned = {}
    if "file_name" in att:
        cleaned["file_name"] = att["file_name"]
    if "mime_type" in att:
        cleaned["mime_type"] = att["mime_type"]
    return cleaned


def clean_step(step):
    """Clean a step, preserving structure but removing dynamic data."""
    cleaned = {}

    if "data" in step:
        cleaned["data"] = step["data"]

    if "execution" in step and isinstance(step["execution"], dict):
        exec_cleaned = {}
        if "status" in step["execution"]:
            exec_cleaned["status"] = step["execution"]["status"]
        if "attachments" in step["execution"] and step["execution"]["attachments"]:
            exec_cleaned["attachments"] = [
                clean_attachment(a) for a in step["execution"]["attachments"]
            ]
        if exec_cleaned:
            cleaned["execution"] = exec_cleaned

    if "steps" in step and step["steps"]:
        cleaned["steps"] = [clean_step(s) for s in step["steps"]]

    return cleaned


def normalize_signature(sig):
    """Normalize Cucumber file-based signatures to be path-independent.

    Cucumber 5 and earlier produce signatures with absolute file paths like:
      512::file:::::::users::gda::...::features::attachment-tests.feature::scenario_name
    Replace the absolute path prefix with just 'file::features::' to make it portable.
    """
    if not sig or "::file::" not in sig:
        return sig
    # Match: <ids>::file::<absolute-path>::features::<rest>
    m = re.match(r"^(.+?::file::).+?(features::.+)$", sig)
    if m:
        return m.group(1) + m.group(2)
    return sig


def clean_result(result):
    """Clean a single test result."""
    cleaned = {}

    for key in ("title", "testops_id", "testops_ids"):
        if key in result and result[key]:
            cleaned[key] = result[key]

    if "signature" in result and result["signature"]:
        cleaned["signature"] = normalize_signature(result["signature"])

    if "execution" in result and isinstance(result["execution"], dict):
        exec_cleaned = {}
        if "status" in result["execution"]:
            exec_cleaned["status"] = result["execution"]["status"]
        if exec_cleaned:
            cleaned["execution"] = exec_cleaned

    if "fields" in result and result["fields"]:
        cleaned["fields"] = result["fields"]

    if "attachments" in result and result["attachments"]:
        cleaned["attachments"] = [clean_attachment(a) for a in result["attachments"]]

    if "steps" in result and result["steps"]:
        cleaned["steps"] = [clean_step(s) for s in result["steps"]]

    if "params" in result and result["params"]:
        cleaned["params"] = result["params"]

    if "param_groups" in result and result["param_groups"]:
        cleaned["param_groups"] = result["param_groups"]

    if "relations" in result and result["relations"]:
        cleaned["relations"] = result["relations"]

    return cleaned


def clean_expected(data):
    """Clean the top-level expected data structure."""
    cleaned = {}

    if "run" in data and isinstance(data["run"], dict):
        run_cleaned = {}
        if "stats" in data["run"]:
            run_cleaned["stats"] = data["run"]["stats"]
        if run_cleaned:
            cleaned["run"] = run_cleaned

    if "results" in data:
        cleaned["results"] = [clean_result(r) for r in data["results"]]

    return cleaned


def main():
    if len(sys.argv) < 2:
        print("Usage: python clean_expected.py <file1.yaml> [file2.yaml ...]")
        sys.exit(1)

    for filepath in sys.argv[1:]:
        with open(filepath, "r") as f:
            data = yaml.safe_load(f)

        if data is None:
            print(f"Skipping empty file: {filepath}")
            continue

        cleaned = clean_expected(data)

        with open(filepath, "w") as f:
            yaml.dump(cleaned, f, default_flow_style=False, allow_unicode=True, sort_keys=False)

        print(f"Cleaned: {filepath}")


if __name__ == "__main__":
    main()
