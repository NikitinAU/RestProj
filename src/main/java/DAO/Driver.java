package DAO;

import lombok.Getter;

@Getter
public enum Driver {
    H2_TEST("jdbc:h2:mem:TEST_GAMES_LIBRARY\\;" +
            "INIT="+
            "runscript from 'src/main/test/resources/Init_DB.sql'\\;" +
            "runscript from 'src/main/test/resources/Fill_DB.sql'"),
    POSTGRES("jdbc:postgresql://localhost/GamesLibrary");

    private String url;

    Driver(String url) {this.url = url;}
}
