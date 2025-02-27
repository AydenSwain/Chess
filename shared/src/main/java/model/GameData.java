package model;

import chess.ChessGame;

public record GameData(int gameId, String whiteUsername, String blackUsername, String gameName, ChessGame game) {}
