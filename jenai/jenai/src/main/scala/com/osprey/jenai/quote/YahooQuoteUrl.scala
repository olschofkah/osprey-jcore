package com.osprey.jenai.quote

import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory

class YahooQuoteUrl(val symbol: String, config: Config) extends QuoteUrl {

  private val __domain = config.getString("yahoo.quote.stock.domain")
  private val __Uri = config.getString("yahoo.quote.stock.path") + symbol + "?" + config.getString("yahoo.quote.stock.module.fieldName") + "=" + config.getString("yahoo.quote.stock.module.price")

  override def url: String = __domain + __Uri

  override def domain: String = __domain

  override def uri: String = __Uri
}

