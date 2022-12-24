package ru.iashinme.homework04.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class InputOutputServiceImpl implements InputOutputService {

    private final Scanner scan;
    private final PrintStream out;

    public InputOutputServiceImpl(@Value("#{ T(java.lang.System).in}") InputStream in,
                                  @Value("#{ T(java.lang.System).out}") PrintStream out) {
        this.out = out;
        this.scan = new Scanner(in);
    }

    @Override
    public void printMessage(String message) {
            out.println(message);
    }

    @Override
    public String readLine() {
        return scan.nextLine();
    }

    @Override
    public int readInt() {
        return scan.nextInt();
    }
}
