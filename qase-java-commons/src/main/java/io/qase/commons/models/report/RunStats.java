package io.qase.commons.models.report;


public class RunStats {
    public int passed;
    public int failed;
    public int skipped;
    public int blocked;
    public int invalid;
    public int muted;
    public int total;

    public RunStats() {
        this.passed = 0;
        this.failed = 0;
        this.skipped = 0;
        this.blocked = 0;
        this.invalid = 0;
        this.muted = 0;
        this.total = 0;
    }

    public void track(ShortReportResult result, boolean muted) {
        switch (result.status) {
            case "passed":
                this.passed++;
                break;
            case "failed":
                this.failed++;
                break;
            case "skipped":
                this.skipped++;
                break;
            case "blocked":
                this.blocked++;
                break;
            case "invalid":
                this.invalid++;
                break;
        }

        this.total++;

        if (muted) {
            this.muted++;
        }
    }
}
