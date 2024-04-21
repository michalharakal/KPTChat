package org.skainet.nn

import de.jugda.knanogpt.core.tensor.Tensor
import de.jugda.knanogpt.core.tensor.Shape
import jp.co.qoncept.tensorkotlin.conv2d

class Conv2D(override val name: String) : Module() {

    val filter = NamedParameter(
        "filter", Tensor(Shape(3, 3, 3, 64))
    )


    /*
    Number of Parameters=(Kernel Height×Kernel Width×Number of Input Channels+1)×Number of Output Channels



    Kernel Width: 3
    Number of Input Channels: 3 (RGB image)
    Number of Output Channels: 64

The calculation would be:
(3×3×3+1)×64=(27+1)×64=28×64=1792
(3×3×3+1)×64=(27+1)×64=28×64=1792
     */
    override
    val params: List<NamedParameter>
        get() = listOf(filter)

    override
    val modules: List<Module>
        get() = TODO("Not yet implemented")

    /*
    Shape Calculation: The output size for both tests is calculated using
    the formula [(W−K+2P)/S]+1,
    where W is the input width/height,
     K is the kernel size,
    P is the padding, and
    S is the stride.
     With a 5x5 input, 3x3 kernel, no padding, and stride of 1,
     the output dimension comes out to be 3x3.



    # Output shape: (batch_size, out_channels, output_height, output_width)
        # Calculating output size: [(W−K+2P)/S]+1 = [(5-3+0)/1]+1 = 3

     */
    override fun forward(input: Tensor): Tensor {
        //input.conv2d(filter, )
        return input.conv2d(
            inChannels = 3,
            outChannels = 64,
            kernel = filter.value,
            stride = 1,
            padding = 0,
            bias = false
        )
    }
}