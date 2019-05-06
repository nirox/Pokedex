package com.mobgen.presentation.game

import com.mobgen.presentation.BaseViewModel

class GameViewModel : BaseViewModel<GameViewModel.GameViewData>() {
    private var mainViewData =
        GameViewData(Status.LOADING)

    init {
        data.value = mainViewData
    }

    class GameViewData(
        override var status: Status
    ) : Data
}
