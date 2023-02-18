package io.github.zekerzhayard.cme_soundsystem;

import java.util.List;
import java.util.Set;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

public class MixinConfigPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        MappingResolver mr = FabricLoader.getInstance().getMappingResolver();
        for (MethodNode mn : targetClass.methods) {
            if (mn.name.equals("<init>")) {
                for (AbstractInsnNode ain :  mn.instructions.toArray())  {
                    if (ain.getOpcode() == Opcodes.PUTFIELD) {
                        FieldInsnNode fin = (FieldInsnNode) ain;
                        if (fin.owner.equals(mr.mapClassName("intermediary", "net.minecraft.class_1140").replace('.', '/')) && fin.name.equals(mr.mapFieldName("intermediary", "net.minecraft.class_1140", "field_18950", "Ljava/util/Map;")) && fin.desc.equals("Ljava/util/Map;")) {
                            mn.instructions.insertBefore(ain, new InsnNode(Opcodes.POP));
                            mn.instructions.insertBefore(ain, new TypeInsnNode(Opcodes.NEW, "java/util/concurrent/ConcurrentHashMap"));
                            mn.instructions.insertBefore(ain, new InsnNode(Opcodes.DUP));
                            mn.instructions.insertBefore(ain, new MethodInsnNode(Opcodes.INVOKESPECIAL, "java/util/concurrent/ConcurrentHashMap", "<init>", "()V", false));
                        }
                    }
                }
            }
        }
    }
}
