package com.company;

import com.company.core.Collection;
import com.company.core.Core;
import com.company.core.Parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Core core = new Core();
        core.searchFile();
        core.script();
    }
}
