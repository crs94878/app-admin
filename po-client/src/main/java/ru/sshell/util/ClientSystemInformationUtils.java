package ru.sshell.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sshell.model.ClientData;
import ru.sshell.model.OS;
import ru.sshell.model.OSType;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.stream.IntStream;


/**
 * Класс для определения инфомации о системы клиента, в которой запущена программа
 */
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
        ClientData clientData =  new ClientData();
        clientData.setOs(OS.getOsByName(System.getProperty(OS_NAME_PROP)));
        clientData.setMacAddr(getMacAddress());
        clientData.setOsType(OSType.spotOSType(System.getProperty(OS_ARCH_PROP)));
        try {
            clientData.setHostname(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return clientData;
    }

    /**
     * Определение mac адреса
     * @return мак адрес
     */
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
