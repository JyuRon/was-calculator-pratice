package org.example;

import org.example.socket.QueryString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryStringTest {
    // List<QueryString>
    @DisplayName("")
    @Test
    void createTest(){

        QueryString queryString = new QueryString("operand","11");
        assertThat(queryString).isNotNull();
    }

}