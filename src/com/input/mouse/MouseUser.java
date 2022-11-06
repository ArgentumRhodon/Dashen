package com.input.mouse;

import com.state.State;
//A basic interface with defined mouse use methods
public interface MouseUser {

    void onClick(State sate);
    void onDrag(State state);

}
