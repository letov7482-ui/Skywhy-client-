package com.skywhy.utils;

public class Animation {
    private double start, end, duration, current, startTime;
    private Easing easing;
    private boolean running = false;

    public Animation(double start, double end, long durationMs, Easing easing) {
        this.start = start;
        this.end = end;
        this.duration = durationMs;
        this.easing = easing;
        this.current = start;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        running = true;
    }

    public double getValue() {
        if (!running) return end;
        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed >= duration) { running = false; return end; }
        double t = elapsed / (double) duration;
        return start + (end - start) * easing.apply(t);
    }

    public enum Easing {
        OUT_QUAD { public double apply(double t) { return 1 - (1-t)*(1-t); } },
        IN_OUT_QUAD { public double apply(double t) { return t<0.5 ? 2*t*t : 1 - Math.pow(-2*t+2, 2)/2; } },
        ELASTIC { public double apply(double t) { return Math.pow(2, -10*t) * Math.sin((t - 0.1) * 5 * Math.PI) + 1; } },
        BOUNCE { public double apply(double t) {
            if (t < 0.5) return 1 - 3 * (1 - 2*t) * (1 - 2*t) / 4;
            double n = t - 0.5;
            return 1 - 3 * (1 - 2*n) * (1 - 2*n) / 4;
        } };
        public abstract double apply(double t);
    }
}
