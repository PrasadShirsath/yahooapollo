package com.wallet

import org.springframework.boot.SpringApplication

object WalletApp {
  def main(args: Array[String]) {
    SpringApplication.run(classOf[WalletAppConfiguration])
  }
}
