package com.tema1.main;




public final class Main {
    private Main() {
    }
    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();
        GameLogic mygame = new GameLogic();
        mygame.PrepareGame(gameInput);
        mygame.StartGame();
        mygame.computebasic();
        mygame.computebonuses();
        mygame.displayscores();
    }
}
