# Roadmap: qase-java

## Milestones

- ✅ **v1.0 Documentation Standardization** — Phases 1-4 (shipped 2026-02-17)
- ✅ **v1.1 FileReporter Spec Compliance** — Phases 5-6 (shipped 2026-02-17)
- ✅ **v1.2 Examples Overhaul** — Phases 7-10 (shipped 2026-02-18)
- ✅ **v1.3 Robustness & Reliability** — Phases 11-13 (shipped 2026-03-05)
- 🚧 **v1.4 Upload Resilience & Observability** — Phases 14-17 (in progress)

## Phases

<details>
<summary>✅ v1.0 Documentation Standardization (Phases 1-4) — SHIPPED 2026-02-17</summary>

Phases 1-4 archived in `.planning/milestones/v1.0-ROADMAP.md`

</details>

<details>
<summary>✅ v1.1 FileReporter Spec Compliance (Phases 5-6) — SHIPPED 2026-02-17</summary>

Phases 5-6 archived in `.planning/milestones/v1.1-ROADMAP.md`

</details>

<details>
<summary>✅ v1.2 Examples Overhaul (Phases 7-10) — SHIPPED 2026-02-18</summary>

Phases 7-10 archived in `.planning/milestones/v1.2-ARCHIVE.md`

</details>

<details>
<summary>✅ v1.3 Robustness & Reliability (Phases 11-13) — SHIPPED 2026-03-05</summary>

- [x] Phase 11: Foundation Hardening (2/2 plans) — completed 2026-03-05
- [x] Phase 12: TestopsReporter Concurrency (1/1 plan) — completed 2026-03-05
- [x] Phase 13: API Resilience + StepStorage (2/2 plans) — completed 2026-03-05

Phases 11-13 archived in `.planning/milestones/v1.3-ROADMAP.md`

</details>

### v1.4 Upload Resilience & Observability (In Progress)

**Milestone Goal:** Fix large attachment upload failures during parallel test execution and make the reporter's behavior observable through clean, actionable logging.

- [x] **Phase 14: Log Sanitization** - Remove binary data from logs and switch to summary-format upload logging (completed 2026-03-10)
- [x] **Phase 15: Upload Resilience Core** - Dynamic timeouts, batch error isolation, and graceful degradation for attachment uploads (completed 2026-03-10)
- [ ] **Phase 16: Upload Performance & Memory** - Parallel attachment uploads and disk-backed attachment storage to reduce memory pressure
- [ ] **Phase 17: Upload Observability** - Structured progress tracking and completion summary statistics

## Phase Details

### Phase 14: Log Sanitization
**Goal**: Log output is clean and actionable — no binary data dumps, only human-readable summaries
**Depends on**: Nothing (independent of upload changes, lowest risk)
**Requirements**: LOGS-01, LOGS-02
**Success Criteria** (what must be TRUE):
  1. Running a test with a 10MB attachment produces no binary content in any log output — only fileName, size, and mimeType appear for attachments
  2. Batch upload log messages show summary format (e.g., "Uploading batch 1: 5 results, 3 attachments, 2.4 MB") instead of full object toString() dumps
  3. Existing log levels (trace/debug/info/warn/error) still produce output at their configured verbosity — nothing is silenced
**Plans**: 1 plan

Plans:
- [x] 14-01-PLAN.md — Sanitize log call sites and add metadata-only attachment logging

### Phase 15: Upload Resilience Core
**Goal**: Attachment upload failures no longer cause data loss — timeouts adapt to file size, failed batches are isolated, and results are always delivered
**Depends on**: Phase 14 (clean logs make debugging resilience behavior much easier)
**Requirements**: UPLD-01, UPLD-03, UPLD-04
**Success Criteria** (what must be TRUE):
  1. Uploading a 50MB attachment uses a timeout proportional to its size (base + size/minimum_speed), not the fixed default timeout
  2. When batch 2 of 5 fails with a network error, batches 3-5 still upload successfully — the asyncError flag no longer kills subsequent batches
  3. When an attachment upload fails after all retries, the test result is still sent to Qase without attachments and a warning log explains what was dropped
  4. A complete test run with mixed healthy/failing attachment uploads finishes without exceptions propagating to the test framework
**Plans**: 2 plans

Plans:
- [ ] 15-01-PLAN.md — Dynamic upload timeout scaling based on file size (UPLD-01)
- [ ] 15-02-PLAN.md — Batch error isolation and graceful attachment degradation (UPLD-03, UPLD-04)

### Phase 16: Upload Performance & Memory
**Goal**: Large test suites with many attachments upload efficiently without exhausting heap memory
**Depends on**: Phase 15 (resilience must be in place before parallelizing uploads)
**Requirements**: UPLD-02, UPLD-05
**Success Criteria** (what must be TRUE):
  1. Attachment uploads execute in parallel using a configurable thread pool — observable via log timestamps showing concurrent upload operations
  2. The thread pool size is configurable and defaults to a sensible value (e.g., 4 threads)
  3. After addResult() is called with a large attachment, the byte[] content is written to a temporary file and the in-memory reference is released — heap usage stays flat even with many pending results
  4. Temporary files are cleaned up after successful upload or at JVM shutdown
**Plans**: 2 plans

Plans:
- [ ] 16-01-PLAN.md — Disk-backed attachment staging with immediate memory release (UPLD-05)
- [ ] 16-02-PLAN.md — Parallel attachment batch upload via configurable thread pool (UPLD-02)

### Phase 17: Upload Observability
**Goal**: Users can understand exactly what the reporter is doing during uploads and review aggregate statistics at run completion
**Depends on**: Phase 15, Phase 16 (progress tracking must wrap the actual upload implementation)
**Requirements**: LOGS-03, LOGS-04
**Success Criteria** (what must be TRUE):
  1. Each upload operation produces a structured log line containing: batch number, file count, total bytes, elapsed time, and retry attempt count
  2. At test run completion, the reporter logs a summary line with: total results sent, total attachments uploaded, total bytes transferred, total upload time, count of failed uploads, and total retry attempts
  3. The progress and summary information is logged at INFO level so users see it by default without enabling debug logging
**Plans**: TBD

Plans:
- [ ] 17-01: TBD

## Progress

**Execution Order:**
Phases execute in numeric order: 14 → 15 → 16 → 17

| Phase | Milestone | Plans Complete | Status | Completed |
|-------|-----------|----------------|--------|-----------|
| 1-4. Documentation | v1.0 | 12/12 | Complete | 2026-02-17 |
| 5-6. Spec Compliance | v1.1 | 3/3 | Complete | 2026-02-17 |
| 7-10. Examples | v1.2 | 10/10 | Complete | 2026-02-18 |
| 11. Foundation Hardening | v1.3 | 2/2 | Complete | 2026-03-05 |
| 12. TestopsReporter Concurrency | v1.3 | 1/1 | Complete | 2026-03-05 |
| 13. API Resilience + StepStorage | v1.3 | 2/2 | Complete | 2026-03-05 |
| 14. Log Sanitization | v1.4 | 1/1 | Complete | 2026-03-10 |
| 15. Upload Resilience Core | 2/2 | Complete    | 2026-03-10 | - |
| 16. Upload Performance & Memory | v1.4 | 0/2 | Not started | - |
| 17. Upload Observability | v1.4 | 0/? | Not started | - |
