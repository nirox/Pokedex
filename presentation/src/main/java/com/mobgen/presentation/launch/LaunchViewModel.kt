package com.mobgen.presentation.launch

import com.mobgen.presentation.BaseViewModel

class LaunchViewModel() : BaseViewModel<LaunchViewModel.LaunchViewData>() {
    private var mainViewData =
        LaunchViewData(Status.LOADING)

    init {
        data.value = mainViewData
    }

    class LaunchViewData(
        override var status: Status
    ) : Data
}