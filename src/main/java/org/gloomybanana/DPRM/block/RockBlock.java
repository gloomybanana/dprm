package org.gloomybanana.DPRM.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class RockBlock extends Block {
    public RockBlock(){
        super(Block.Properties
                .create(Material.ROCK)
                .hardnessAndResistance(5));
    }
}
