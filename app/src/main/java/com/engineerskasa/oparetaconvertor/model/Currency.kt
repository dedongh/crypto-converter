package com.engineerskasa.oparetaconvertor.model


import com.google.gson.annotations.SerializedName

data class Currency(
    var data: List<Data>? = null,
    var status: Status? = null
) {
    data class Data(
        @SerializedName("circulating_supply")
        var circulatingSupply: Double? = null,
        @SerializedName("cmc_rank")
        var cmcRank: Int? = null,
        @SerializedName("date_added")
        var dateAdded: String? = null,
        var id: Int? = null,
        @SerializedName("last_updated")
        var lastUpdated: String? = null,
        @SerializedName("max_supply")
        var maxSupply: Long? = null,
        var name: String? = null,
        @SerializedName("num_market_pairs")
        var numMarketPairs: Long? = null,
        var platform: Any? = null,
        var quote: Quote? = null,
        var slug: String? = null,
        var symbol: String? = null,
        var tags: List<String>? = null,
        @SerializedName("total_supply")
        var totalSupply: Double? = null
    ) {
        data class Quote(
            @SerializedName("USD")
            var usd: QuoteInterface? = null,
            @SerializedName("EUR")
            var eur: QuoteInterface? = null,
            @SerializedName("UGX")
            var ugx: QuoteInterface? = null
        ) {
            data class QuoteInterface(
                @SerializedName("fully_diluted_market_cap")
                var fullyDilutedMarketCap: Double? = null,
                @SerializedName("last_updated")
                var lastUpdated: String? = null,
                @SerializedName("market_cap")
                var marketCap: Double? = null,
                @SerializedName("market_cap_dominance")
                var marketCapDominance: Double? = null,
                @SerializedName("percent_change_1h")
                var percentChange1h: Double? = null,
                @SerializedName("percent_change_24h")
                var percentChange24h: Double? = null,
                @SerializedName("percent_change_30d")
                var percentChange30d: Double? = null,
                @SerializedName("percent_change_60d")
                var percentChange60d: Double? = null,
                @SerializedName("percent_change_7d")
                var percentChange7d: Double? = null,
                @SerializedName("percent_change_90d")
                var percentChange90d: Double? = null,
                var price: Double? = null,
                @SerializedName("volume_24h")
                var volume24h: Double? = null,
                @SerializedName("volume_change_24h")
                var volumeChange24h: Double? = null
            )
        }
    }

    data class Status(
        @SerializedName("credit_count")
        var creditCount: Int? = null,
        var elapsed: Int? = null,
        @SerializedName("error_code")
        var errorCode: Int? = null,
        @SerializedName("error_message")
        var errorMessage: Any? = null,
        var notice: Any? = null,
        var timestamp: String? = null,
        @SerializedName("total_count")
        var totalCount: Int? = null
    )
}