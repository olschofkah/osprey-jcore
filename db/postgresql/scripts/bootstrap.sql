CREATE TABLE "oc_security" (
	"symbol" VARCHAR(10) NOT NULL UNIQUE,
	"cusip" VARCHAR(9),
	"company_name" VARCHAR(255) NOT NULL,
	"country" VARCHAR(10),
	"state" VARCHAR(2),
	"employee_cnt" int8,
	"industry" VARCHAR(50),
	"sector" VARCHAR(50),
	"exchange_cd" char(1),
	"currency" VARCHAR(5),
	"previous_close" float8,
	"last_update_ts" timestamptz NOT NULL,
	"desc" VARCHAR(1024),
	CONSTRAINT oc_security_pk PRIMARY KEY ("symbol")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "oc_exchange" (
	"exchange_cd" char(1) NOT NULL,
	"exhange" VARCHAR(255) NOT NULL,
	CONSTRAINT oc_exchange_pk PRIMARY KEY ("exchange_cd")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "oc_security_ext_stat_log" (
	"symbol" VARCHAR(10) NOT NULL,
	"timestamp" timestamptz NOT NULL,
	"date" DATE NOT NULL,
	"52_week_high" float8,
	"52_week_low" float8,
	"50_day_average" float8,
	"200_day_average" float8,
	"average_volume" int8,
	"regular_market_volume" int8,
	"10_day_avg_volume" int8,
	"market_cap" int8,
	"beta" float8,
	"return_on_assets" float8,
	"return_on_equity" float8,
	"forward_pe" float8,
	"trailing_pe" float8,
	"profit_margins" float8,
	"float8_shares" float8,
	"shares_outstanding" float8,
	"shares_short" float8,
	"shares_short_prior_month" float8,
	"held_pct_insiders" float8,
	"held_pct_institutions" float8,
	"short_ratio" float8,
	"short_percent_of_float8" float8,
	"book_value" float8,
	"price_to_book" float8,
	"earnings_qtr_growth" float8,
	"net_income_to_common" float8,
	"trailing_eps" float8,
	"forward_eps" float8,
	"peg_ratio" float8,
	"enterprise_value" float8,
	"enterprise_to_revenue" float8,
	"enterprise_to_ebitda" float8,
	"total_cash" float8,
	"total_cash_per_share" float8,
	"ebitda" float8,
	"total_debt" float8,
	"quick_ratio" float8,
	"current_ratio" float8,
	"total_revenue" float8,
	"debt_to_equity" float8,
	"revenue_per_share" float8,
	"gross_profits" float8,
	"free_cashflow" float8,
	"operating_cashflow" float8,
	"earnings_growth" float8,
	"revenue_growth" float8,
	"gross_margins" float8,
	"ebitda_margins" float8,
	"operating_margins" float8,
	"price_to_sales" float8,
	"earnings_avg" float8,
	"earnings_low" float8,
	"earnings_high" float8,
	"revenue_avg" float8,
	"revenue_low" float8,
	"revenue_high" float8,
	CONSTRAINT oc_security_ext_stat_log_pk PRIMARY KEY ("symbol","timestamp")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "oc_security_quote" (
	"symbol" VARCHAR(10) NOT NULL,
	"timestamp" timestamptz,
	"last" float8,
	"bid" float8,
	"ask" float8,
	"bid_size" int8,
	"ask_size" int8,
	"volume" int8,
	"open" float8,
	"close" float8,
	"high" float8,
	"low" float8,
	"data_currency_cd" char(2) NOT NULL,
	"open_interest" int8,
	CONSTRAINT oc_security_quote_pk PRIMARY KEY ("symbol","timestamp")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "oc_quote_data_currency" (
	"data_currency_cd" char(2) NOT NULL DEFAULT 'dr',
	"data_currency_desc" VARCHAR(255) NOT NULL,
	CONSTRAINT oc_quote_data_currency_pk PRIMARY KEY ("data_currency_cd")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "oc_security_next_events" (
	"symbol" VARCHAR(10) NOT NULL,
	"next_earnings_date_est_low" DATE,
	"next_earnings_date_est_high" DATE,
	"next_div_date" DATE,
	"next_ex_div_date" DATE,
	"next_revenue" DATE,
	"timestamp" timestamptz NOT NULL,
	CONSTRAINT oc_security_next_events_pk PRIMARY KEY ("symbol")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "oc_security_event" (
	"symbol" VARCHAR(10) NOT NULL,
	"date" DATE NOT NULL,
	"event_type_cd" char(1) NOT NULL,
	"amt" float8 NOT NULL,
	"timestamp" timestamptz NOT NULL,
	CONSTRAINT oc_security_event_pk PRIMARY KEY ("symbol","date","event_type_cd")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "oc_secrity_event_type" (
	"event_type_cd" char(1) NOT NULL,
	"event_desc" VARCHAR(255) NOT NULL,
	CONSTRAINT oc_secrity_event_type_pk PRIMARY KEY ("event_type_cd")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "oc_security_int_stat_log" (
	"symbol" VARCHAR(10) NOT NULL,
	"timestamp" timestamptz NOT NULL,
	"volatility_30" float8,
	"volatility_90" float8,
	"volatility_360" float8,
	"ema_4" float8,
	"ema_9" float8,
	"ema_12" float8,
	"ema_15" float8,
	"ema_26" float8,
	"ema_50" float8,
	"ema_200" float8,
	"beta_30" float8,
	"beta_90" float8,
	"beta_360" float8,
	"alpha_for_ema" float8,
	CONSTRAINT oc_security_int_stat_log_pk PRIMARY KEY ("symbol","timestamp")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "oc_security_ochl_hist" (
	"symbol" VARCHAR(255) NOT NULL,
	"date" DATE NOT NULL,
	"open" float8,
	"close" float8,
	"high" float8,
	"low" float8,
	"adj_close" float8,
	"volume" float8,
	"timestamp" timestamptz NOT NULL,
	CONSTRAINT oc_security_ochl_hist_pk PRIMARY KEY ("symbol","date")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "tha_hot_shit" (
	"symbol" VARCHAR(10) NOT NULL,
	"date" DATE NOT NULL,
	"timestamp" timestamptz NOT NULL,
	"payload" jsonb NOT NULL, -- screen, strategy
	CONSTRAINT tha_hot_shit_pk PRIMARY KEY ("symbol","date")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "oc_trigger" (
	"symbol" VARCHAR(10) NOT NULL,
	"timestamp" timestamptz NOT NULL,
	"payload" jsonb NOT NULL, -- screen, strategy, trade
	CONSTRAINT oc_trigger_pk PRIMARY KEY ("symbol")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "oc_optn_chain" (
	"symbol" VARCHAR(10) NOT NULL,
	"optn_symbol" VARCHAR(255) NOT NULL,
	"expiration_date" DATE NOT NULL,
	"strike" float8 NOT NULL,
	"optn_type_cd" char(1) NOT NULL,
	"timestamp" timestamptz NOT NULL,
	CONSTRAINT oc_optn_chain_pk PRIMARY KEY ("symbol","optn_symbol")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "oc_optn_type" (
	"optn_type_cd" char(1) NOT NULL,
	"optn_type_desc" VARCHAR(255) NOT NULL,
	CONSTRAINT oc_optn_type_pk PRIMARY KEY ("optn_type_cd")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "oc_optn_chain_quote" (
	"symbol" VARCHAR(10) NOT NULL,
	"optn_symbol" VARCHAR(255) NOT NULL,
	"timestamp" timestamptz NOT NULL,
	"last" float8,
	"bid" float8,
	"ask" float8,
	"bid_size" float8,
	"ask_size" float8,
	"volume" int8,
	"open_interest" int8,
	"implied_volatility" float8,
	"delta" float8,
	"gamma" float8,
	"theta" float8,
	"vega" float8,
	CONSTRAINT oc_optn_chain_quote_pk PRIMARY KEY ("symbol","optn_symbol","timestamp")
) WITH (
  OIDS=FALSE
);



ALTER TABLE "oc_security" ADD CONSTRAINT "oc_security_fk0" FOREIGN KEY ("exchange_cd") REFERENCES "oc_exchange"("exchange_cd");


ALTER TABLE "oc_security_ext_stat_log" ADD CONSTRAINT "oc_security_ext_stat_log_fk0" FOREIGN KEY ("symbol") REFERENCES "oc_security"("symbol");

ALTER TABLE "oc_security_quote" ADD CONSTRAINT "oc_security_quote_fk0" FOREIGN KEY ("symbol") REFERENCES "oc_security"("symbol");
ALTER TABLE "oc_security_quote" ADD CONSTRAINT "oc_security_quote_fk1" FOREIGN KEY ("data_currency_cd") REFERENCES "oc_quote_data_currency"("data_currency_cd");


ALTER TABLE "oc_security_next_events" ADD CONSTRAINT "oc_security_next_events_fk0" FOREIGN KEY ("symbol") REFERENCES "oc_security"("symbol");

ALTER TABLE "oc_security_event" ADD CONSTRAINT "oc_security_event_fk0" FOREIGN KEY ("symbol") REFERENCES "oc_security"("symbol");
ALTER TABLE "oc_security_event" ADD CONSTRAINT "oc_security_event_fk1" FOREIGN KEY ("event_type_cd") REFERENCES "oc_secrity_event_type"("event_type_cd");


ALTER TABLE "oc_security_int_stat_log" ADD CONSTRAINT "oc_security_int_stat_log_fk0" FOREIGN KEY ("symbol") REFERENCES "oc_security"("symbol");

ALTER TABLE "oc_security_ochl_hist" ADD CONSTRAINT "oc_security_ochl_hist_fk0" FOREIGN KEY ("symbol") REFERENCES "oc_security"("symbol");

ALTER TABLE "tha_hot_shit" ADD CONSTRAINT "tha_hot_shit_fk0" FOREIGN KEY ("symbol") REFERENCES "oc_security"("symbol");
ALTER TABLE "oc_trigger" ADD CONSTRAINT "oc_trigger_fk0" FOREIGN KEY ("symbol") REFERENCES "oc_security"("symbol");

ALTER TABLE "oc_optn_chain" ADD CONSTRAINT "oc_optn_chain_fk0" FOREIGN KEY ("symbol") REFERENCES "oc_security"("symbol");
ALTER TABLE "oc_optn_chain" ADD CONSTRAINT "oc_optn_chain_fk1" FOREIGN KEY ("optn_type_cd") REFERENCES "oc_optn_type"("optn_type_cd");


ALTER TABLE "oc_optn_chain_quote" ADD CONSTRAINT "oc_optn_chain_quote_fk0" FOREIGN KEY ("symbol","optn_symbol") REFERENCES "oc_optn_chain"("symbol","optn_symbol");