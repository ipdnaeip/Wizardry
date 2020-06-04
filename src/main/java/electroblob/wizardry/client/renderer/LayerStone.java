package electroblob.wizardry.client.renderer;

import electroblob.wizardry.block.BlockStatue;
import electroblob.wizardry.client.ClientProxy;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

/**
 * Layer used to render the stone texture on a petrified creature.
 * 
 * @author Electroblob
 * @since Wizardry 1.2
 */
public class LayerStone extends LayerTiledOverlay<EntityLivingBase> {

	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/blocks/stone.png");

	public LayerStone(RenderLivingBase<?> renderer){
		super(renderer);
	}

	@Override
	public boolean shouldRender(EntityLivingBase entity, float partialTicks){
		return entity.getEntityData().getBoolean(BlockStatue.PETRIFIED_NBT_KEY);
	}

	@Override
	public ResourceLocation getTexture(EntityLivingBase entity, float partialTicks){
		ResourceLocation breakingTexture = ClientProxy.renderStatue.getBlockBreakingTexture();
		return breakingTexture == null ? TEXTURE : breakingTexture;
	}

	// FIXME: Does not work with zombie pigmen, I have no idea why.
	// I believe the issue is with the TESR actually, since LayerFrost works fine

	@Override
	public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, float scale){

		// N.B. It's one or the other because this effectively gets called twice via RenderStatue
		if(ClientProxy.renderStatue.getBlockBreakingTexture() != null){

			GlStateManager.enableBlend();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

			super.doRenderLayer(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);

			GlStateManager.disableBlend();

		}else{
			super.doRenderLayer(entity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
		}
	}

}