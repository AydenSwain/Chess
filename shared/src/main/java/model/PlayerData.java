package model;

import chess.ChessGame;

public record PlayerData(ChessGame.TeamColor playerColor, int gameID) {
}
