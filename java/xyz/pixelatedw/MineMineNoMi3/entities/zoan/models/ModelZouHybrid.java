package xyz.pixelatedw.MineMineNoMi3.entities.zoan.models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelZouHybrid extends ModelZoanMorph
{
	public ModelRenderer body1;
	public ModelRenderer body2;
	public ModelRenderer rightleg1;
	public ModelRenderer rightleg2;
	public ModelRenderer leftleg1;
	public ModelRenderer leftleg2;
	public ModelRenderer rightarm1;
	public ModelRenderer rightarm2;
	public ModelRenderer leftarm1;
	public ModelRenderer leftarm2;
	public ModelRenderer tail1;
	public ModelRenderer tail2;
	public ModelRenderer head;
	public ModelRenderer rightear;
	public ModelRenderer leftear;
	public ModelRenderer snout;
	public ModelRenderer snout2;
	public ModelRenderer snout3;
	public ModelRenderer tuskA1;
	public ModelRenderer tuskA2;
	public ModelRenderer tuskB1;
	public ModelRenderer tuskB2;

	public ModelZouHybrid()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.rightear = new ModelRenderer(this, 0, 19);
		this.rightear.setRotationPoint(-3.5F, -15.800000190734863F, -4.0F);
		this.rightear.addBox(-6.0F, -6.0F, 0.0F, 6, 8, 1, 0.0F);
		this.setRotateAngle(rightear, -0.2365421852028136F, 1.0305797642360894F, -0.27401146395144504F);
		this.leftarm1 = new ModelRenderer(this, 0, 46);
		this.leftarm1.setRotationPoint(9.0F, -9.600000381469727F, 1.0F);
		this.leftarm1.addBox(0.0F, -2.0F, -2.5F, 5, 10, 5, 0.0F);
		this.setRotateAngle(leftarm1, 0.0F, -0.0F, -0.03490658476948738F);
		this.leftarm2 = new ModelRenderer(this, 21, 46);
		this.leftarm2.setRotationPoint(11.800000190734863F, -1.7999999523162842F, 1.0F);
		this.leftarm2.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
		this.body2 = new ModelRenderer(this, 35, 25);
		this.body2.setRotationPoint(0.0F, 11.199999809265137F, 3.0F);
		this.body2.addBox(-8.5F, -10.0F, -7.0F, 17, 10, 10, 0.0F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.setRotationPoint(0.0F, -12.199999809265137F, -3.0F);
		this.head.addBox(-4.0F, -9.0F, -4.5F, 8, 9, 9, 0.0F);
		this.setRotateAngle(head, 0.05235987901687623F, -0.0F, 0.0F);
		this.tuskB1 = new ModelRenderer(this, 15, 19);
		this.tuskB1.setRotationPoint(2.299999952316284F, -12.800000190734863F, -6.5F);
		this.tuskB1.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(tuskB1, -0.34906584024429316F, -0.17453292012214658F, 0.0F);
		this.rightleg2 = new ModelRenderer(this, 63, 46);
		this.rightleg2.setRotationPoint(-5.0F, 18.0F, 0.699999988079071F);
		this.rightleg2.addBox(-2.5F, 0.0F, -2.5F, 5, 6, 5, 0.0F);
		this.leftleg1 = new ModelRenderer(this, 42, 46);
		this.leftleg1.setRotationPoint(5.0F, 11.100000381469727F, 1.0F);
		this.leftleg1.addBox(-2.5F, 0.0F, -2.5F, 5, 7, 5, 0.0F);
		this.setRotateAngle(leftleg1, -0.05235987901687623F, -0.0F, 0.0F);
		this.leftleg2 = new ModelRenderer(this, 63, 46);
		this.leftleg2.setRotationPoint(5.0F, 18.0F, 0.699999988079071F);
		this.leftleg2.addBox(-2.5F, 0.0F, -2.5F, 5, 6, 5, 0.0F);
		this.tuskA2 = new ModelRenderer(this, 15, 25);
		this.tuskA2.setRotationPoint(-2.5F, -9.199999809265137F, -7.699999809265137F);
		this.tuskA2.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(tuskA2, -0.5235987901687622F, 0.19198621809482574F, 0.0F);
		this.tuskB2 = new ModelRenderer(this, 15, 25);
		this.tuskB2.setRotationPoint(2.5F, -9.199999809265137F, -7.699999809265137F);
		this.tuskB2.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(tuskB2, -0.5235987901687622F, -0.19198621809482574F, 0.0F);
		this.rightleg1 = new ModelRenderer(this, 42, 46);
		this.rightleg1.setRotationPoint(-5.0F, 11.100000381469727F, 1.0F);
		this.rightleg1.addBox(-2.5F, 0.0F, -2.5F, 5, 7, 5, 0.0F);
		this.setRotateAngle(rightleg1, -0.05235987901687623F, -0.0F, 0.0F);
		this.rightarm2 = new ModelRenderer(this, 21, 46);
		this.rightarm2.setRotationPoint(-11.800000190734863F, -1.7999999523162842F, 1.0F);
		this.rightarm2.addBox(-2.5F, 0.0F, -2.5F, 5, 10, 5, 0.0F);
		this.leftear = new ModelRenderer(this, 0, 19);
		this.leftear.setRotationPoint(3.5F, -15.800000190734863F, -4.0F);
		this.leftear.addBox(0.0F, -6.0F, 0.0F, 6, 8, 1, 0.0F);
		this.setRotateAngle(leftear, -0.2365421852028136F, -1.0305797642360894F, 0.27401146395144504F);
		this.snout3 = new ModelRenderer(this, 120, 57);
		this.snout3.setRotationPoint(-1.0F, -0.30000001192092896F, -8.0F);
		this.snout3.addBox(0.0F, 0.0F, 0.0F, 2, 5, 2, 0.0F);
		this.setRotateAngle(snout3, 0.17453292012214658F, -0.0F, 0.0F);
		this.snout = new ModelRenderer(this, 112, 35);
		this.snout.setRotationPoint(-2.0F, -13.0F, -7.5F);
		this.snout.addBox(0.0F, 0.0F, 0.0F, 4, 7, 4, 0.0F);
		this.setRotateAngle(snout, -0.2094395160675048F, -0.0F, 0.0F);
		this.rightarm1 = new ModelRenderer(this, 0, 46);
		this.rightarm1.setRotationPoint(-9.0F, -9.600000381469727F, 1.0F);
		this.rightarm1.addBox(-5.0F, -2.0F, -2.5F, 5, 10, 5, 0.0F);
		this.setRotateAngle(rightarm1, 0.0F, -0.0F, 0.03490658476948738F);
		this.tail2 = new ModelRenderer(this, 119, 0);
		this.tail2.setRotationPoint(0.0F, 14.199999809265137F, 7.5F);
		this.tail2.addBox(-1.0F, 0.0F, -1.0F, 2, 3, 2, 0.0F);
		this.setRotateAngle(tail2, 0.43633231520652765F, -0.0F, 0.0F);
		this.snout2 = new ModelRenderer(this, 116, 47);
		this.snout2.setRotationPoint(-1.5F, -6.199999809265137F, -8.5F);
		this.snout2.addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, 0.0F);
		this.body1 = new ModelRenderer(this, 35, 0);
		this.body1.setRotationPoint(0.0F, -2.5999999046325684F, 2.700000047683716F);
		this.body1.addBox(-9.0F, -10.0F, -7.0F, 18, 14, 10, 0.0F);
		this.setRotateAngle(body1, 0.08726646006107329F, -0.0F, 0.0F);
		this.tail1 = new ModelRenderer(this, 114, 0);
		this.tail1.setRotationPoint(0.0F, 9.699999809265137F, 5.5F);
		this.tail1.addBox(-0.5F, 0.0F, -0.5F, 1, 6, 1, 0.0F);
		this.setRotateAngle(tail1, 0.43633231520652765F, -0.0F, 0.0F);
		this.tuskA1 = new ModelRenderer(this, 15, 19);
		this.tuskA1.setRotationPoint(-2.299999952316284F, -12.800000190734863F, -6.5F);
		this.tuskA1.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(tuskA1, -0.34906584024429316F, 0.17453292012214658F, 0.0F);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		this.rightear.render(f5);
		this.leftarm1.render(f5);
		this.leftarm2.render(f5);
		this.body2.render(f5);
		this.head.render(f5);
		this.tuskB1.render(f5);
		this.rightleg2.render(f5);
		this.leftleg1.render(f5);
		this.leftleg2.render(f5);
		this.tuskA2.render(f5);
		this.tuskB2.render(f5);
		this.rightleg1.render(f5);
		this.rightarm2.render(f5);
		this.leftear.render(f5);
		this.snout3.render(f5);
		this.snout.render(f5);
		this.rightarm1.render(f5);
		this.tail2.render(f5);
		this.snout2.render(f5);
		this.body1.render(f5);
		this.tail1.render(f5);
		this.tuskA1.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public ModelRenderer getHandRenderer()
	{
		return null;
	}
}
