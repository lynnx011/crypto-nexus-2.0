package com.example.cryptocurrency.utils

object Constants {

    const val COIN_MARKET_CAP_URL = "https://pro-api.coinmarketcap.com/"
    const val NEWS_URL = "https://newsapi.org/v2/"
    const val COIN_GECKO_URL = "https://api.coingecko.com/api/v3/"
    const val BLOCK_SPAN_URL = "https://api.blockspan.com/v1/"
    const val COIN_MARKET_CAP_API_KEY = "d09431fd-f23b-4168-860e-7719f7e39a82"
    //    private const val BLOCK_API_KEY = "sUnkWjd1DZ0vFcNQ6fy8ujbHg1TwR2qH"
    const val NEWS_API_KEY = "d35eefdb54434e38877107bb4991bef9"
    const val BLOCK_SPAN_API_KEY = "H5dZzfE36IqiztXHquMYNG7jwteiE4l8"
}

var totalTrans = 0.0

enum class CRYPTO(val type: String){
    MARKET_LIST("market_list"), TOP_CRYPTO("top_crypto"), GAINER("top_gainers"), LOSER("top_losers")
}