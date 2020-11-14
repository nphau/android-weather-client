package vn.futagroup.android.shared.delegates

interface Transformer<Input, Output> {

    fun transform(input: Input): Output

}
