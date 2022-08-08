package ru.sshell.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import ru.sshell.model.ClientData;
import ru.sshell.model.OS;
import ru.sshell.model.OSType;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.stream.IntStream;


/**
 * Класс для определения инфомации о системы клиента, в которой запущена программа
 */
@ParametersAreNonnullByDefault
public class ClientSystemInformationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientSystemInformationUtils.class);
    private static final String OS_NAME_PROP = "os.name";
    private static final String OS_ARCH_PROP = "os.arch";

    private static final String MAC_ADDR_FORMAT = "%02X";
    private static final String MAC_ADDR_SEPARATOR = ":";

    public static final ClientData CLIENT_DATA = initClientSystemData();

    private ClientSystemInformationUtils() {}

    /**
     * Определение системных данных клиента
     * @return системные данные клиента
     */
    private static ClientData initClientSystemData() {
        InetAddress innetAddress = null;
        try {
            innetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            LOGGER.error("Error while fill client hostname", e);
        }
        return ClientData.builder()
                .setOs(OS.getOs(System.getProperty(OS_NAME_PROP)))
                .setMacAddr(getMacAddress())
                .setOsType(OSType.getOsType(System.getProperty(OS_ARCH_PROP)))
                .setHostname(innetAddress != null
                        ? innetAddress.getHostName() : null)
                .build();
    }

    /**
     * Определение mac адреса
     * @return мак адрес
     */
    @NonNull
    private static String getMacAddress() {
        StringBuilder macAddressBuilder = new StringBuilder();
        try {

            InetAddress ipAddress = InetAddress.getLocalHost();
            NetworkInterface networkInterface =  spotNetWorkInt(ipAddress);
            if (networkInterface != null) {
                byte[] macAddressBytes = networkInterface.getHardwareAddress();
                IntStream.range(0, macAddressBytes.length).forEachOrdered(macAddressByteIndex -> {
                    String macAddressHexByte = String.format(MAC_ADDR_FORMAT, macAddressBytes[macAddressByteIndex]);
                    macAddressBuilder.append(macAddressHexByte);
                    if (macAddressByteIndex != macAddressBytes.length - 1) {
                        macAddressBuilder.append(MAC_ADDR_SEPARATOR);
                    }
                });
            }
        } catch (UnknownHostException | SocketException | NullPointerException e) {
            LOGGER.error("Error while getting mac address. Maybe u connect to wifi");
        }
        return macAddressBuilder.toString();
    }

    /**
     * Определение инет интерфейса
     * @param localHOstName имя локал хост
     * @return              корректный интерфейс
     * @throws SocketException ошибка сокета
     */
    @Nullable
    private static NetworkInterface spotNetWorkInt(InetAddress localHOstName) throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface.getHardwareAddress() != null) {
                return networkInterface;
            }
        }
        return null;
    }

}
