package org.example;

import org.example.socket.QueryStrings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class QueryStringsTest {
    @DisplayName("")
    @Test
    void test(){
        QueryStrings queryStrings = new QueryStrings("operand1=11&operator=*&operand2=55");
        assertThat(queryStrings).isNotNull();

    }

}