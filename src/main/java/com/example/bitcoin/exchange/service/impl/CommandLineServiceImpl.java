package com.example.bitcoin.exchange.service.impl;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.Scanner;

import com.example.bitcoin.exchange.exception.CurrencyNotAvailableException;
import com.example.bitcoin.exchange.exception.HistoryNotAvailableException;
import com.example.bitcoin.exchange.service.CoinDeskService;
import com.example.bitcoin.exchange.service.CommandLineService;
import com.example.bitcoin.exchange.viewdata.BitcoinPriceRange;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandLineServiceImpl implements CommandLineService {

    private static final String QUIT_KEY = "quit";

    private static final Logger LOG = LoggerFactory.getLogger(CommandLineServiceImpl.class);

    private String typedCurrency;

    private CurrencyAsker asker;

    public static class CurrencyAsker {
        private final Scanner scanner;
        private final PrintStream out;

        public CurrencyAsker(InputStream in, PrintStream out) {
            scanner = new Scanner(in);
            this.out = out;
        }

        public String ask(String message) {
            this.out.println(message);
            return scanner.next();
        }

    }

    public CommandLineServiceImpl() {
        this.asker = new CurrencyAsker(System.in, System.out);
    }

    public CommandLineServiceImpl(InputStream in, PrintStream out) {
        this.asker = new CurrencyAsker(in, out);
    }

    @Override
    public void run() {
        printProgramInfo();
        execute();
    }

    private void printProgramInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\nThis program will find the current Bitcoin price ");
        builder.append(" and the lowest/highes Bitcoin rate in the last 30 days, for the given Currency.");
        builder.append("\n\nTo exit the program just type '").append(QUIT_KEY).append("'");
        LOG.info(builder.toString());
    }

    private void execute() {
        while (isProgramRunnig()) {
            try {
                readTheCurrency();
                searchAndPrintTheBitcoinPriceRange();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        System.exit(0);
    }

    private void readTheCurrency() {
        this.typedCurrency = asker.ask("\nPlease provide the currency code: ");
        if (StringUtils.isNotBlank(this.typedCurrency))
            this.typedCurrency = this.typedCurrency.trim();
        System.out.println("Typed currecy: " + this.typedCurrency);
    }

    private boolean isProgramRunnig() {
        return StringUtils.isBlank(this.typedCurrency) || !QUIT_KEY.equalsIgnoreCase(this.typedCurrency);
    }

    private void searchAndPrintTheBitcoinPriceRange()
            throws CurrencyNotAvailableException, HistoryNotAvailableException {
        CoinDeskService coinDeskService = new CoinDeskServiceImpl();
        BitcoinPriceRange result = coinDeskService.getBitcoinPriceRangeForCurrency(this.typedCurrency, LocalDate.now());
        System.out.println(result);
    }

}
