package com.osprey.jenai.utils

import org.apache.logging.log4j.LogManager

trait Logging {
  // TODO Switch to a scala based logger for better functional synergy
  @transient lazy val logger = LogManager.getLogger(this.getClass);
}