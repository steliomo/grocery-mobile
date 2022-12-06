package mz.co.commandline.grocery.util.alert

interface DialogListner<T> {
    fun perform(t: T)
}