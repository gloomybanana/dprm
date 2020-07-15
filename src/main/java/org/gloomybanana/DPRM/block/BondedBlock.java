package org.gloomybanana.DPRM.block;


import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BondedBlock extends RotatedPillarBlock {

    public BondedBlock() {
        super(Properties
                .create(Material.BAMBOO)
                .hardnessAndResistance(1.5F)
                .sound(SoundType.CLOTH)
                .lightValue(15));
    }
}
