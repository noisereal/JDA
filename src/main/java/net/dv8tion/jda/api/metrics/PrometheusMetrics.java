package net.dv8tion.jda.api.metrics;

import io.prometheus.client.Counter;

public class PrometheusMetrics
{
    public static final Counter EVENTS_TOTAL = Counter.build()
            .name("jda_events_received_total")
            .labelNames("shard_id")
            .register();
}
