ALTER TABLE "oc_security" DROP CONSTRAINT IF EXISTS "oc_security_fk0";

ALTER TABLE "oc_security_ext_stat_log" DROP CONSTRAINT IF EXISTS "oc_security_ext_stat_log_fk0";

ALTER TABLE "oc_security_quote" DROP CONSTRAINT IF EXISTS "oc_security_quote_fk0";

ALTER TABLE "oc_security_quote" DROP CONSTRAINT IF EXISTS "oc_security_quote_fk1";

ALTER TABLE "oc_security_next_events" DROP CONSTRAINT IF EXISTS "oc_security_next_events_fk0";

ALTER TABLE "oc_security_event" DROP CONSTRAINT IF EXISTS "oc_security_event_fk0";

ALTER TABLE "oc_security_event" DROP CONSTRAINT IF EXISTS "oc_security_event_fk1";

ALTER TABLE "oc_security_int_stat_log" DROP CONSTRAINT IF EXISTS "oc_security_int_stat_log_fk0";

ALTER TABLE "oc_security_ochl_hist" DROP CONSTRAINT IF EXISTS "oc_security_ochl_hist_fk0";

ALTER TABLE "tha_hot_list" DROP CONSTRAINT IF EXISTS "tha_hot_list_fk0";

ALTER TABLE "oc_trigger" DROP CONSTRAINT IF EXISTS "oc_trigger_fk0";

ALTER TABLE "oc_optn_chain" DROP CONSTRAINT IF EXISTS "oc_optn_chain_fk0";

ALTER TABLE "oc_optn_chain" DROP CONSTRAINT IF EXISTS "oc_optn_chain_fk1";

ALTER TABLE "oc_optn_chain_quote" DROP CONSTRAINT IF EXISTS "oc_optn_chain_quote_fk0";

DROP TABLE IF EXISTS "oc_security";

DROP TABLE IF EXISTS "oc_exchange";

DROP TABLE IF EXISTS "oc_security_ext_stat_log";

DROP TABLE IF EXISTS "oc_security_quote";

DROP TABLE IF EXISTS "oc_quote_data_currency";

DROP TABLE IF EXISTS "oc_security_next_events";

DROP TABLE IF EXISTS "oc_security_event";

DROP TABLE IF EXISTS "oc_secrity_event_type";

DROP TABLE IF EXISTS "oc_security_int_stat_log";

DROP TABLE IF EXISTS "oc_security_ochl_hist";

DROP TABLE IF EXISTS "tha_hot_list";

DROP TABLE IF EXISTS "oc_trigger";

DROP TABLE IF EXISTS "oc_optn_chain";

DROP TABLE IF EXISTS "oc_optn_type";

DROP TABLE IF EXISTS "oc_optn_chain_quote";
