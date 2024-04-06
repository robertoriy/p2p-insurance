package ru.nsu.insurance.p2p.service.insurance;

import java.math.BigInteger;

public final class EthereumUtils {
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    private static final BigInteger ETH_MULTIPLIER = BigInteger.valueOf(1000000000000000000L);

    private EthereumUtils() {
    }

    public static BigInteger convertToETH(long value) {
        return ETH_MULTIPLIER.multiply(BigInteger.valueOf(value));
    }
}
