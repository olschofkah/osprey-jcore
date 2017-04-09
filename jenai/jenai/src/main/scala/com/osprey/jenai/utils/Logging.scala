package com.osprey.jenai.utils

import org.apache.logging.log4j.LogManager

trait Logging {
  @transient lazy val logger = LogManager.getLogger(this.getClass);
}