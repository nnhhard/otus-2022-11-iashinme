package ru.iashinme.service;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class InputOutputServiceImpl implements InputOutputService {

    @Override
    public void printMessage(String message) {
            System.out.println(message);
    }

    @Override
    public String readLine(){
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }

    @Override
    public int readInt(){
        Scanner in = new Scanner(System.in);
        return in.nextInt();
    }
}
