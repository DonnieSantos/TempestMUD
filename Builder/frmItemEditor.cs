using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace Tempest_Builder

{
  public class frmItemEditor : System.Windows.Forms.Form

  {
    private item active_item;
    private itemlist item_list;
    private bool saved, active, changed;
    private bool watch_change;
    private bool control_dtype;
    private string original_id;
    private string exact_holder = "";

    #region GUI variables
    private System.Windows.Forms.Label label1;
    private System.Windows.Forms.TextBox txtID;
    private System.Windows.Forms.Label label2;
    private System.Windows.Forms.TextBox txtName;
    private System.Windows.Forms.Label label3;
    private System.Windows.Forms.TextBox txtLookAtDesc;
    private System.Windows.Forms.Label label4;
    private System.Windows.Forms.Label label5;
    private System.Windows.Forms.TextBox txtLevel;
    private System.Windows.Forms.Label label6;
    private System.Windows.Forms.Label label7;
    private System.Windows.Forms.Label label8;
    private System.Windows.Forms.TextBox txtHP;
    private System.Windows.Forms.TextBox txtMN;
    private System.Windows.Forms.TextBox txtMV;
    private System.Windows.Forms.TextBox txtReqWis;
    private System.Windows.Forms.TextBox txtReqInt;
    private System.Windows.Forms.TextBox txtReqCon;
    private System.Windows.Forms.TextBox txtReqDex;
    private System.Windows.Forms.TextBox txtReqStr;
    private System.Windows.Forms.Label label19;
    private System.Windows.Forms.Label label20;
    private System.Windows.Forms.Label label21;
    private System.Windows.Forms.Label label22;
    private System.Windows.Forms.Label label23;
    private System.Windows.Forms.Label lblWorth;
    private System.Windows.Forms.TextBox txtWorth;
    private System.Windows.Forms.Label label16;
    private System.Windows.Forms.Label label17;
    private System.Windows.Forms.Label label18;
    private System.Windows.Forms.Label label24;
    private System.Windows.Forms.TextBox txtHit;
    private System.Windows.Forms.TextBox txtDam;
    private System.Windows.Forms.TextBox txtAC;
    private System.Windows.Forms.TextBox txtRes;
    private System.Windows.Forms.Label label25;
    private System.Windows.Forms.TextBox txtBoardName;
    private System.Windows.Forms.Label label26;
    private System.Windows.Forms.Button cmdSave;
    private System.Windows.Forms.Button cmdCancel;
    private System.Windows.Forms.Label label27;
    private System.Windows.Forms.TextBox txtMaxItems;
    private System.Windows.Forms.Label label28;
    private System.Windows.Forms.Label label29;
    private System.Windows.Forms.CheckBox chkAs;
    private System.Windows.Forms.CheckBox chkSh;
    private System.Windows.Forms.CheckBox chkSm;
    private System.Windows.Forms.CheckBox chkSc;
    private System.Windows.Forms.CheckBox chkWl;
    private System.Windows.Forms.CheckBox chkIl;
    private System.Windows.Forms.CheckBox chkWi;
    private System.Windows.Forms.CheckBox chkMg;
    private System.Windows.Forms.CheckBox chkAl;
    private System.Windows.Forms.CheckBox chkHl;
    private System.Windows.Forms.CheckBox chkDc;
    private System.Windows.Forms.CheckBox chkPr;
    private System.Windows.Forms.CheckBox chkMo;
    private System.Windows.Forms.CheckBox chkDr;
    private System.Windows.Forms.CheckBox chkCl;
    private System.Windows.Forms.CheckBox chkMc;
    private System.Windows.Forms.CheckBox chkMe;
    private System.Windows.Forms.CheckBox chkSb;
    private System.Windows.Forms.CheckBox chkRa;
    private System.Windows.Forms.CheckBox chkRg;
    private System.Windows.Forms.CheckBox chkTh;
    private System.Windows.Forms.CheckBox chkAp;
    private System.Windows.Forms.CheckBox chkCr;
    private System.Windows.Forms.CheckBox chkDk;
    private System.Windows.Forms.CheckBox chkPa;
    private System.Windows.Forms.CheckBox chkBr;
    private System.Windows.Forms.CheckBox chkKn;
    private System.Windows.Forms.CheckBox chkWa;
    private System.Windows.Forms.CheckBox chkNeck;
    private System.Windows.Forms.CheckBox chkRFinger;
    private System.Windows.Forms.CheckBox chkRWrist;
    private System.Windows.Forms.CheckBox chkLWrist;
    private System.Windows.Forms.CheckBox chkREar;
    private System.Windows.Forms.CheckBox chkLEar;
    private System.Windows.Forms.CheckBox chkLegs;
    private System.Windows.Forms.CheckBox chkWaist;
    private System.Windows.Forms.CheckBox chkHands;
    private System.Windows.Forms.CheckBox chkBack;
    private System.Windows.Forms.CheckBox chkFeet;
    private System.Windows.Forms.CheckBox chkHead;
    private System.Windows.Forms.CheckBox chkBody;
    private System.Windows.Forms.CheckBox chkRHand;
    private System.Windows.Forms.CheckBox chkLHand;
    private System.Windows.Forms.CheckBox chkShield;
    private System.Windows.Forms.CheckBox chkWeapon;
    private System.Windows.Forms.ComboBox cboPotionSpells;
    private System.Windows.Forms.Button cmdAddSpell;
    private System.Windows.Forms.Button cmdRemoveSpell;
    private System.Windows.Forms.ComboBox cboLoadIDs;
    private System.Windows.Forms.Button cmdAddID;
    private System.Windows.Forms.Button cmdRemoveID;
    private System.Windows.Forms.TextBox txtLName;
    private System.Windows.Forms.CheckBox chkLFinger;
    private System.Windows.Forms.TextBox txtGroundDesc;
    private System.Windows.Forms.Label label30;
    private System.Windows.Forms.Label label9;
    private System.Windows.Forms.Label label10;
    private System.Windows.Forms.Label label31;
    private System.Windows.Forms.Label label32;
    private System.Windows.Forms.Label label11;
    private System.Windows.Forms.CheckBox chkEquipment;
    private System.Windows.Forms.CheckBox chkEqContainer;
    private System.Windows.Forms.CheckBox chkNormal;
    private System.Windows.Forms.CheckBox chkFood;
    private System.Windows.Forms.CheckBox chkDrink;
    private System.Windows.Forms.CheckBox chkRefill;
    private System.Windows.Forms.CheckBox chkScroll;
    private System.Windows.Forms.CheckBox chkFountain;
    private System.Windows.Forms.Button cmdRemInnate;
    private System.Windows.Forms.Button cmdAddInnate;
    private System.Windows.Forms.ComboBox cboInnate;
    private System.Windows.Forms.Label label12;
    private System.Windows.Forms.Label label13;
    private System.Windows.Forms.TextBox txtCharges;
    private System.Windows.Forms.Label label14;
    private System.Windows.Forms.CheckBox chkContainer;
    private System.Windows.Forms.CheckBox chkBoard;
    private System.Windows.Forms.Label label15;
    private System.Windows.Forms.CheckBox chkStab;
    private System.Windows.Forms.CheckBox chkBludgeon;
    private System.Windows.Forms.CheckBox chkPierce;
    private System.Windows.Forms.CheckBox chkSlash;
    private System.Windows.Forms.CheckBox chkArrow;
    private System.Windows.Forms.CheckBox chkStar;
    private System.Windows.Forms.CheckBox chkWhip;
    private System.Windows.Forms.CheckBox chkChop;
    private System.Windows.Forms.CheckBox chkFist;
    private System.Windows.Forms.CheckBox chkThrust;
    private System.Windows.Forms.CheckBox chkGouge;
    private System.Windows.Forms.CheckBox chkBlast;
    private System.Windows.Forms.ComboBox cboCharged;
    private System.Windows.Forms.CheckBox chkPerish;
    private System.Windows.Forms.CheckBox chkIngredient;
    private System.Windows.Forms.CheckBox chkGlowing;
    private System.Windows.Forms.CheckBox chkHumming;
    private System.Windows.Forms.CheckBox chkNoGet;
    private System.Windows.Forms.CheckBox chkNoSacrifice;
    private System.Windows.Forms.CheckBox chkNoDrop;
    private System.Windows.Forms.CheckBox chkBlocker;
    private System.Windows.Forms.Label label33;
    private System.Windows.Forms.Label label34;
    private System.Windows.Forms.TextBox txtBDir;
    private System.Windows.Forms.TextBox txtBType;
    private System.Windows.Forms.Label label35;
    private System.Windows.Forms.TextBox txtUses;
    private System.Windows.Forms.Button cmdRemoveCharged;
    private System.Windows.Forms.Button cmdAddCharged;
    private System.Windows.Forms.Label label36;
    private System.Windows.Forms.TextBox txtLiquid;
    private System.Windows.Forms.Button cmdSelectAll;
    private System.Windows.Forms.CheckBox chkEqLight;
    private System.Windows.Forms.Label label37;
    private System.Windows.Forms.TextBox txtLightTicks;
    private System.Windows.Forms.CheckBox chkInvisible;
    private System.Windows.Forms.CheckBox chkUntouchable;
    private System.Windows.Forms.CheckBox chkGood;
    private System.Windows.Forms.CheckBox chkEvil;
    private System.Windows.Forms.CheckBox chkNeutral;
    private System.Windows.Forms.CheckBox chkBurning;
    private System.Windows.Forms.CheckBox chkFreezing;
    private System.Windows.Forms.CheckBox chkShocking;
    private System.Windows.Forms.CheckBox chkNoRent;
    private System.Windows.Forms.Button cmdExact;
    private System.Windows.Forms.CheckBox chkExact;
    private System.ComponentModel.Container components = null;
    #endregion

    public frmItemEditor(itemlist ilist)

    {
      InitializeComponent();
      item_list = ilist;

      for (int i=0; i<this.Controls.Count; i++)
        if ((Controls[i] != cmdCancel) && (Controls[i] != cmdSave))
          Controls[i].GotFocus += new EventHandler(this.Change_Click);

      chkEquipment.CheckedChanged += new EventHandler(TypeChecked);
      chkEqContainer.CheckedChanged += new EventHandler(TypeChecked);
      chkEqLight.CheckedChanged += new EventHandler(TypeChecked);
      chkNormal.CheckedChanged += new EventHandler(TypeChecked);
      chkContainer.CheckedChanged += new EventHandler(TypeChecked);
      chkBoard.CheckedChanged += new EventHandler(TypeChecked);
      chkFood.CheckedChanged += new EventHandler(TypeChecked);
      chkDrink.CheckedChanged += new EventHandler(TypeChecked);
      chkRefill.CheckedChanged += new EventHandler(TypeChecked);
      chkScroll.CheckedChanged += new EventHandler(TypeChecked);
      chkFountain.CheckedChanged += new EventHandler(TypeChecked);
      chkBlocker.CheckedChanged += new EventHandler(TypeChecked);
      
      chkStab.CheckedChanged += new EventHandler(DTypeChecked);
      chkSlash.CheckedChanged += new EventHandler(DTypeChecked);
      chkPierce.CheckedChanged += new EventHandler(DTypeChecked);
      chkBludgeon.CheckedChanged += new EventHandler(DTypeChecked);
      chkChop.CheckedChanged += new EventHandler(DTypeChecked);
      chkWhip.CheckedChanged += new EventHandler(DTypeChecked);
      chkArrow.CheckedChanged += new EventHandler(DTypeChecked);
      chkStar.CheckedChanged += new EventHandler(DTypeChecked);
      chkFist.CheckedChanged += new EventHandler(DTypeChecked);
      chkThrust.CheckedChanged += new EventHandler(DTypeChecked);
      chkGouge.CheckedChanged += new EventHandler(DTypeChecked);
      chkBlast.CheckedChanged += new EventHandler(DTypeChecked);
      
      chkWeapon.CheckedChanged += new EventHandler(WeaponChecked);
    }

    public void new_item()

    {
      int current;
      int last = 0;
      bool found = false;
      active_item = new item();

      if (item_list.get_size() == 0) {
        active_item.id = frmMain.get_zone_id() + "00";
        found = true; }

      else if (item_list.get_item(0).id != frmMain.get_zone_id() + "00") {
        found = true;
        active_item.id = frmMain.get_zone_id() + "00"; }

      else for (int i=0; i<item_list.get_size(); i++)

      {
        current = Int32.Parse(item_list.get_item(i).id);
        if (i == 0) last = current - 1;

        if (current != last + 1)

        {
          found = true;
          last++;
          active_item.id = last.ToString();
          i = item_list.get_size();
        }

        last = current;
      }

      if (!found)

      {
        string str_id = item_list.get_item(item_list.get_size()-1).id;
        int int_id = Int32.Parse(str_id);
        int_id++;
        active_item.id = int_id.ToString();
      }

      edit_item(active_item);
    }

    public void edit_item(item item_pointer)

    {
      active_item = item_pointer;

      original_id        = item_pointer.id;
      txtID.Text         = item_pointer.id;
      txtName.Text       = item_pointer.name;
      txtLName.Text      = item_pointer.lname;
      
      if (item_pointer.exact_look)
      
      {
        chkExact.Checked = true;
        txtLookAtDesc.ReadOnly = true;
        txtLookAtDesc.Text = "EXACT DESCRIPTION";
        txtLookAtDesc.BackColor = Color.FromName("Control");
        exact_holder = item_pointer.exact_description;
      }
      
      else
      
      {
        txtLookAtDesc.ReadOnly = false;
        txtLookAtDesc.BackColor = Color.FromName("Window");
        txtLookAtDesc.Text = item_pointer.lookatdesc;
      }
        
      txtGroundDesc.Text = item_pointer.grounddesc;

      #region Bonuses
      txtHP.Text  = item_pointer.hp;
      txtMN.Text  = item_pointer.mana;
      txtMV.Text  = item_pointer.move;
      txtRes.Text = item_pointer.res;
      txtAC.Text  = item_pointer.ac;
      txtHit.Text = item_pointer.hit;
      txtDam.Text = item_pointer.dam;
      txtWorth.Text = item_pointer.worth;
      #endregion

      #region Requirements
      txtReqStr.Text = item_pointer.reqstatStr;
      txtReqDex.Text = item_pointer.reqstatDex;
      txtReqWis.Text = item_pointer.reqstatWis;
      txtReqCon.Text = item_pointer.reqstatCon;
      txtReqInt.Text = item_pointer.reqstatInt;
      txtLevel.Text  = item_pointer.level;
      #endregion

      #region Flags
      chkPerish.Checked = item_pointer.perish;
      chkIngredient.Checked = item_pointer.ingredient;
      chkNoGet.Checked = item_pointer.no_get;
      chkNoSacrifice.Checked = item_pointer.no_sacrifice;
      chkNoDrop.Checked = item_pointer.no_drop;
      chkGlowing.Checked = item_pointer.glowing;
      chkHumming.Checked = item_pointer.humming;
      chkInvisible.Checked = item_pointer.invisible;
      chkUntouchable.Checked = item_pointer.untouchable;
      chkNoRent.Checked = item_pointer.no_rent;
      chkBurning.Checked = item_pointer.burning;
      chkFreezing.Checked = item_pointer.freezing;
      chkShocking.Checked = item_pointer.shocking;
      chkNeutral.Checked = item_pointer.neutral;
      chkGood.Checked = item_pointer.good;
      chkEvil.Checked = item_pointer.evil;
      #endregion

      #region Item Types
      chkNormal.Checked = item_pointer.normal;
      chkContainer.Checked = item_pointer.container;
      chkBoard.Checked = item_pointer.board;
      chkScroll.Checked = item_pointer.scroll;
      chkEquipment.Checked = item_pointer.equipment;
      chkEqContainer.Checked = item_pointer.eq_container;
      chkDrink.Checked = item_pointer.consumable_drink;
      chkRefill.Checked = item_pointer.refillable_drink;
      chkFountain.Checked = item_pointer.fountain;
      chkFood.Checked = item_pointer.food;
      chkEqLight.Checked = item_pointer.light;

      if (item_pointer.equipment || item_pointer.eq_container || item_pointer.light)

      {
        toggle_dtype(true);
        toggle_classes(true);
        toggle_places(true);
        toggle_requirements(true);
      }

      else

      {
        toggle_dtype(false);
        toggle_classes(false);
        toggle_places(false);
        toggle_requirements(false);
      }

      if (item_pointer.light)

      {
        txtLightTicks.Enabled = true;
        txtLightTicks.Text = item_pointer.lightticks;
      }

      else

      {
        txtLightTicks.Enabled = false;
        txtLightTicks.Text = "";
      }

      #endregion

      #region Places
      chkWeapon.Checked = item_pointer.weapon;
      chkShield.Checked = item_pointer.shield;
      chkLHand.Checked = item_pointer.lhand;
      chkRHand.Checked = item_pointer.rhand;
      chkBody.Checked = item_pointer.body;
      chkHead.Checked = item_pointer.head;
      chkFeet.Checked = item_pointer.feet;
      chkBack.Checked = item_pointer.back;
      chkHands.Checked = item_pointer.hands;
      chkWaist.Checked = item_pointer.waist;
      chkLegs.Checked = item_pointer.legs;
      chkLEar.Checked = item_pointer.lear;
      chkREar.Checked = item_pointer.rear;
      chkLWrist.Checked = item_pointer.lwrist;
      chkRWrist.Checked = item_pointer.rwrist;
      chkLFinger.Checked = item_pointer.lfinger;
      chkRFinger.Checked = item_pointer.rfinger;
      chkNeck.Checked = item_pointer.neck;
      #endregion

      #region Classes
      chkWa.Checked = item_pointer.Wa;
      chkKn.Checked = item_pointer.Kn;
      chkBr.Checked = item_pointer.Br;
      chkPa.Checked = item_pointer.Pa;
      chkDk.Checked = item_pointer.Dk;
      chkCr.Checked = item_pointer.Cr;
      chkAp.Checked = item_pointer.Ap;
      chkTh.Checked = item_pointer.Th;
      chkAs.Checked = item_pointer.As;
      chkRg.Checked = item_pointer.Rg;
      chkRa.Checked = item_pointer.Ra;
      chkSb.Checked = item_pointer.Sb;
      chkMe.Checked = item_pointer.Me;
      chkMc.Checked = item_pointer.Mc;
      chkCl.Checked = item_pointer.Cl;
      chkDr.Checked = item_pointer.Dr;
      chkMo.Checked = item_pointer.Mo;
      chkPr.Checked = item_pointer.Pr;
      chkDc.Checked = item_pointer.Dc;
      chkHl.Checked = item_pointer.Hl;
      chkAl.Checked = item_pointer.Al;
      chkMg.Checked = item_pointer.Mg;
      chkWi.Checked = item_pointer.Wi;
      chkIl.Checked = item_pointer.Il;
      chkWl.Checked = item_pointer.Wl;
      chkSc.Checked = item_pointer.Sc;
      chkSm.Checked = item_pointer.Sm;
      chkSh.Checked = item_pointer.Sh;
      #endregion

      #region Container
      if (item_pointer.container)

      {
        chkContainer.Checked = true;
        txtMaxItems.Enabled = true;
        txtMaxItems.Text = item_pointer.max_items;
        cboLoadIDs.Items.Clear();

        for (int i=0; i<active_item.loadIDs.Count; i++)
          cboLoadIDs.Items.Add((string)active_item.loadIDs[i]);
      }

      else

      {
        txtMaxItems.Enabled = false;
        txtMaxItems.Text = "";
        chkContainer.Checked = false;
        cboLoadIDs.Items.Clear();
        cboLoadIDs.Text = "";
        cboLoadIDs.Enabled = false;
      }

      #endregion

      #region Scrolls

      chkScroll.Checked = false;
      cboPotionSpells.Enabled = false;
      cboPotionSpells.Text = "";
      cboPotionSpells.Items.Clear();

      if (item_pointer.scroll)

      {
        chkScroll.Checked = true;
        cboPotionSpells.Enabled = true;
        cboPotionSpells.Items.Clear();

        for (int i=0; i<active_item.potion_spells.Count; i++)
          cboPotionSpells.Items.Add((string)active_item.potion_spells[i]);
      }

      #endregion

      #region Equipment Spells
      if (item_pointer.equipment || item_pointer.light || item_pointer.eq_container)

      {
        cboInnate.Enabled = true;
        cboCharged.Enabled = true;
        txtCharges.Enabled = true;
        cboInnate.Items.Clear();
        cboCharged.Items.Clear();

        txtCharges.Text = item_pointer.charges;

        for (int i=0; i<active_item.innate_spells.Count; i++)
          cboInnate.Items.Add((string)active_item.innate_spells[i]);

        for (int i=0; i<active_item.charged_spells.Count; i++)
          cboCharged.Items.Add((string)active_item.charged_spells[i]);
      }

      else

      {
        cboInnate.Enabled = false;
        cboCharged.Enabled = false;
        txtCharges.Enabled = false;
        cboInnate.Items.Clear();
        cboCharged.Items.Clear();
        txtCharges.Text = "";
      }

      if (item_pointer.eq_container)

      {
        cboInnate.Enabled = true;
        cboCharged.Enabled = true;
        txtCharges.Enabled = true;
        cboLoadIDs.Enabled = true;
        txtMaxItems.Enabled = true;
        cboInnate.Items.Clear();
        cboCharged.Items.Clear();
        cboLoadIDs.Items.Clear();

        txtMaxItems.Text = item_pointer.max_items;
        txtCharges.Text = item_pointer.charges;

        for (int i=0; i<active_item.innate_spells.Count; i++)
          cboInnate.Items.Add((string)active_item.innate_spells[i]);

        for (int i=0; i<active_item.charged_spells.Count; i++)
          cboCharged.Items.Add((string)active_item.charged_spells[i]);

        for (int i=0; i<active_item.loadIDs.Count; i++)
          cboLoadIDs.Items.Add((string)active_item.loadIDs[i]);
      }
      #endregion

      #region Board
      if (item_pointer.board)

      {
        chkBoard.Checked = true;
        txtBoardName.Text = item_pointer.board_name;
      }

      else

      {
        chkBoard.Checked = false;
        txtBoardName.Text = "";
        txtBoardName.Enabled = false;
      }
      #endregion

      #region Damage Types
      chkStab.Checked = item_pointer.stab;
      chkSlash.Checked = item_pointer.slash;
      chkPierce.Checked = item_pointer.pierce;
      chkBludgeon.Checked = item_pointer.bludgeon;
      chkChop.Checked = item_pointer.chop;
      chkWhip.Checked = item_pointer.whip;
      chkArrow.Checked = item_pointer.arrow;
      chkStar.Checked = item_pointer.star;
      chkFist.Checked = item_pointer.fist;
      chkThrust.Checked = item_pointer.thrust;
      chkGouge.Checked = item_pointer.gouge;
      chkBlast.Checked =item_pointer.blast;
      #endregion

      #region Refill Drink
      if ((item_pointer.refillable_drink) || (item_pointer.consumable_drink))

      {
        txtUses.Enabled = true;
        txtUses.Text = item_pointer.uses;
        txtLiquid.Enabled = true;
        txtLiquid.Text = item_pointer.liquid;
        
        if (item_pointer.consumable_drink)
        
        {
          txtUses.Enabled = true;
          txtUses.Text = "1";
          txtUses.ReadOnly = true;
        }          
      }

      else

      {
        txtUses.Enabled = false;
        txtUses.Text = "";
        txtLiquid.Enabled = false;
        txtLiquid.Text = "";
        txtUses.ReadOnly = false;
      }

      if (item_pointer.fountain)

      {
        chkFountain.Enabled = true;
        txtLiquid.Enabled = true;
        txtLiquid.Text = item_pointer.liquid;
      }
      #endregion

      #region Blockers
      if (item_pointer.blocker)

      {
        chkBlocker.Checked = true;
        txtBType.Enabled = true;
        txtBDir.Enabled = true;
        txtBType.Text = item_pointer.btype;
        txtBDir.Text = item_pointer.bdir;
      }

      else

      {
        chkBlocker.Checked = false;
        txtBType.Enabled = false;
        txtBDir.Enabled = false;
        txtBType.Text = "";
        txtBDir.Text = "";
      }
      #endregion

      saved  = false;
      active = true;
      changed = false;
    }

    public void check_object()

    {
      if (txtName.Text == "") active_item.name = "Empty";
      if (txtLName.Text == "") active_item.lname = "Empty";
      if (txtLookAtDesc.Text == "") active_item.lookatdesc = "Empty";
      if (txtGroundDesc.Text == "") active_item.grounddesc = "Empty";

      if (chkRefill.Checked)
        if (txtLiquid.Text == "") active_item.liquid = "Empty";

      if (txtBoardName.Text == "")
        active_item.board_name = txtName.Text;

      try { Int32.Parse(txtHP.Text); }
      catch (System.FormatException) { active_item.hp = "0"; }
      try { Int32.Parse(txtMN.Text); }
      catch (System.FormatException) { active_item.mana = "0"; }
      try { Int32.Parse(txtMV.Text); }
      catch (System.FormatException) { active_item.move = "0"; }
      try { Int32.Parse(txtRes.Text); }
      catch (System.FormatException) { active_item.res = "0"; }
      try { Int32.Parse(txtAC.Text); }
      catch (System.FormatException) { active_item.ac = "0"; }
      try { Int32.Parse(txtHit.Text); }
      catch (System.FormatException) { active_item.hit = "0"; }
      try { Int32.Parse(txtDam.Text); }
      catch (System.FormatException) { active_item.dam = "0"; }
      try { Int32.Parse(txtWorth.Text); }
      catch (System.FormatException) { active_item.worth = "0"; }
      try { Int32.Parse(txtUses.Text); }
      catch (System.FormatException) { active_item.uses = "0"; }
      try { Int32.Parse(txtCharges.Text); }
      catch (System.FormatException) { active_item.charges = "0"; }
      try { Int32.Parse(txtReqStr.Text); }
      catch (System.FormatException) { active_item.reqstatStr = "0"; }
      try { Int32.Parse(txtReqDex.Text); }
      catch (System.FormatException) { active_item.reqstatDex = "0"; }
      try { Int32.Parse(txtReqCon.Text); }
      catch (System.FormatException) { active_item.reqstatCon = "0"; }
      try { Int32.Parse(txtReqWis.Text); }
      catch (System.FormatException) { active_item.reqstatWis = "0"; }
      try { Int32.Parse(txtReqInt.Text); }
      catch (System.FormatException) { active_item.reqstatInt = "0"; }
      try { Int32.Parse(txtMaxItems.Text); }
      catch (System.FormatException) { active_item.max_items = "0"; }
      try { Int32.Parse(txtLevel.Text); }
      catch (System.FormatException) { active_item.level = "0"; }
      try { Int32.Parse(txtLightTicks.Text); }
      catch (System.FormatException) { active_item.lightticks = "0"; }
    }

    public void save_item()

    {
      active_item.id         = txtID.Text;
      active_item.name       = Utility.clear_whitespace(txtName.Text);
      active_item.lname      = Utility.clear_whitespace(txtLName.Text);
      active_item.lookatdesc = Utility.clear_whitespace(txtLookAtDesc.Text);
      active_item.grounddesc = Utility.clear_whitespace(txtGroundDesc.Text);
      
      if (chkExact.Checked)
      
      {
        active_item.exact_look = true;
        active_item.exact_description = exact_holder;
      }
      
      else
      
      {
        active_item.exact_look = false;
        active_item.exact_description = "";
      }

      active_item.hp      = txtHP.Text;
      active_item.mana    = txtMN.Text;
      active_item.move    = txtMV.Text;
      active_item.res     = txtRes.Text;
      active_item.ac      = txtAC.Text;
      active_item.hit     = txtHit.Text;
      active_item.dam     = txtDam.Text;
      active_item.worth   = txtWorth.Text;
      active_item.btype = txtBType.Text;
      active_item.bdir = txtBDir.Text;
      active_item.uses = txtUses.Text;
      active_item.max_items = txtMaxItems.Text;
      active_item.lightticks = txtLightTicks.Text;
      active_item.board_name = txtBoardName.Text;

      active_item.normal = chkNormal.Checked;
      active_item.container = chkContainer.Checked;
      active_item.board = chkBoard.Checked;
      active_item.scroll = chkScroll.Checked;
      active_item.equipment = chkEquipment.Checked;
      active_item.eq_container = chkEqContainer.Checked;
      active_item.consumable_drink = chkDrink.Checked;
      active_item.refillable_drink = chkRefill.Checked;
      active_item.fountain = chkFountain.Checked;
      active_item.food = chkFood.Checked;
      active_item.blocker = chkBlocker.Checked;
      active_item.light = chkEqLight.Checked;
      active_item.liquid = txtLiquid.Text;

      active_item.perish = chkPerish.Checked;
      active_item.ingredient = chkIngredient.Checked;
      active_item.no_get = chkNoGet.Checked;
      active_item.no_sacrifice = chkNoSacrifice.Checked;
      active_item.no_drop = chkNoDrop.Checked;
      active_item.glowing = chkGlowing.Checked;
      active_item.humming = chkHumming.Checked;
      active_item.invisible = chkInvisible.Checked;
      active_item.untouchable = chkUntouchable.Checked;
      active_item.neutral = chkNeutral.Checked;
      active_item.evil = chkEvil.Checked;
      active_item.good = chkGood.Checked;
      active_item.burning = chkBurning.Checked;
      active_item.freezing = chkFreezing.Checked;
      active_item.shocking = chkShocking.Checked;
      active_item.no_rent = chkNoRent.Checked;

      active_item.reqstatStr = txtReqStr.Text;
      active_item.reqstatDex = txtReqDex.Text;
      active_item.reqstatWis = txtReqWis.Text;
      active_item.reqstatCon = txtReqCon.Text;
      active_item.reqstatInt = txtReqInt.Text;
      active_item.level      = txtLevel.Text;

      active_item.Wa = chkWa.Checked;
      active_item.Kn = chkKn.Checked;
      active_item.Br = chkBr.Checked;
      active_item.Pa = chkPa.Checked;
      active_item.Dk = chkDk.Checked;
      active_item.Cr = chkCr.Checked;
      active_item.Ap = chkAp.Checked;
      active_item.Th = chkTh.Checked;
      active_item.As = chkAs.Checked;
      active_item.Rg = chkRg.Checked;
      active_item.Ra = chkRa.Checked;
      active_item.Sb = chkSb.Checked;
      active_item.Me = chkMe.Checked;
      active_item.Mc = chkMc.Checked;
      active_item.Cl = chkCl.Checked;
      active_item.Dr = chkDr.Checked;
      active_item.Mo = chkMo.Checked;
      active_item.Pr = chkPr.Checked;
      active_item.Dc = chkDc.Checked;
      active_item.Hl = chkHl.Checked;
      active_item.Al = chkAl.Checked;
      active_item.Mg = chkMg.Checked;
      active_item.Wi = chkWi.Checked;
      active_item.Il = chkIl.Checked;
      active_item.Wl = chkWl.Checked;
      active_item.Sc = chkSc.Checked;
      active_item.Sm = chkSm.Checked;
      active_item.Sh = chkSh.Checked;

      active_item.weapon = chkWeapon.Checked;
      active_item.shield = chkShield.Checked;
      active_item.lhand = chkLHand.Checked;
      active_item.rhand = chkRHand.Checked;
      active_item.body = chkBody.Checked;
      active_item.head = chkHead.Checked;
      active_item.feet = chkFeet.Checked;
      active_item.back = chkBack.Checked;
      active_item.hands = chkHands.Checked;
      active_item.waist = chkWaist.Checked;
      active_item.legs = chkLegs.Checked;
      active_item.lear = chkLEar.Checked;
      active_item.rear = chkREar.Checked;
      active_item.lwrist = chkLWrist.Checked;
      active_item.rwrist = chkRWrist.Checked;
      active_item.lfinger = chkLFinger.Checked;
      active_item.rfinger = chkRFinger.Checked;
      active_item.neck = chkNeck.Checked;

      active_item.stab = chkStab.Checked;
      active_item.slash = chkSlash.Checked;
      active_item.pierce = chkPierce.Checked;
      active_item.bludgeon = chkBludgeon.Checked;
      active_item.chop = chkChop.Checked;
      active_item.whip = chkWhip.Checked;
      active_item.arrow = chkArrow.Checked;
      active_item.star = chkStar.Checked;
      active_item.fist = chkFist.Checked;
      active_item.thrust = chkThrust.Checked;
      active_item.gouge = chkGouge.Checked;
      active_item.blast = chkBlast.Checked;

      active_item.loadIDs = new ArrayList();

      for (int i=0; i<cboLoadIDs.Items.Count; i++)
        active_item.loadIDs.Add(cboLoadIDs.Items[i]);

      active_item.potion_spells = new ArrayList();

      for (int i=0; i<cboPotionSpells.Items.Count; i++)
        active_item.potion_spells.Add((string)cboPotionSpells.Items[i]);

      active_item.innate_spells = new ArrayList();

      for (int i=0; i<cboInnate.Items.Count; i++)
        active_item.innate_spells.Add((string)cboInnate.Items[i]);

      active_item.charged_spells = new ArrayList();

      for (int i=0; i<cboCharged.Items.Count; i++)
        active_item.charged_spells.Add((string)cboCharged.Items[i]);

      active_item.charges = txtCharges.Text;

      check_object();
    }

    public item get_item()

    {
      return active_item;
    }

    public bool get_saved()

    {
      return saved;
    }

    public bool validate()

    {
      if (Utility.number(txtID.Text) == 0) {
        MessageBox.Show("Invalid Item ID Number.  Cannot save.");
        return false; }

      if (txtID.Text == "") {
        MessageBox.Show("Missing item ID Number.  Cannot save.");
        return false; }

      if (chkBlocker.Checked)

      {
        string temp_bdir = Utility.lowercase(txtBDir.Text);
        if ((temp_bdir == "n") || (temp_bdir == "north")) temp_bdir = "N";
        if ((temp_bdir == "s") || (temp_bdir == "south")) temp_bdir = "S";
        if ((temp_bdir == "e") || (temp_bdir == "east"))  temp_bdir = "E";
        if ((temp_bdir == "w") || (temp_bdir == "west"))  temp_bdir = "W";
        if ((temp_bdir == "u") || (temp_bdir == "up"))    temp_bdir = "U";
        if ((temp_bdir == "d") || (temp_bdir == "down"))  temp_bdir = "D";
        txtBDir.Text = temp_bdir;

        if (txtBType.Text == "") {
          MessageBox.Show("Missing blocker type. Cannot save.");
          return false; }

        if ((temp_bdir != "N") && (temp_bdir != "S") && (temp_bdir != "E")
          && (temp_bdir != "W") && (temp_bdir != "U") && (temp_bdir != "D")) {
          MessageBox.Show("Invalid Blocker Direction.  Cannot save.");
          return false; }
      }

      if (!chkNormal.Checked && !chkContainer.Checked && !chkBoard.Checked &&
          !chkScroll.Checked && !chkEquipment.Checked && !chkEqContainer.Checked &&
          !chkDrink.Checked  && !chkRefill.Checked    && !chkFountain.Checked &&
          !chkFood.Checked   && !chkEqLight.Checked   && !chkBlocker.Checked) {
          MessageBox.Show("You must choose a dominate item type before saving.");
          return false; }

      int n = Int32.Parse(frmMain.get_zone_id());
      int m = (item_list.get_size() / 100);
      int min_id = n * 100;
      int max_id = min_id + 99 + (100*m);
      int id_num = Int32.Parse(txtID.Text);

      if ((id_num < min_id) || (id_num > max_id))

      {
        if (MessageBox.Show ("Item ID " + id_num.ToString() + " does not fall within this Zone's Range. (" +
        min_id.ToString() + "-" + max_id.ToString() + ").  Do you want to save anyway?", "Item ID out of bounds.",
        MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
          return true;

        return false;
      }

      return true;
    }


    #region Windows Form Designer generated code
    private void InitializeComponent()
    {
      System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(frmItemEditor));
      this.txtID = new System.Windows.Forms.TextBox();
      this.label1 = new System.Windows.Forms.Label();
      this.label2 = new System.Windows.Forms.Label();
      this.txtName = new System.Windows.Forms.TextBox();
      this.label3 = new System.Windows.Forms.Label();
      this.txtLookAtDesc = new System.Windows.Forms.TextBox();
      this.label4 = new System.Windows.Forms.Label();
      this.label5 = new System.Windows.Forms.Label();
      this.txtLevel = new System.Windows.Forms.TextBox();
      this.chkAs = new System.Windows.Forms.CheckBox();
      this.chkSh = new System.Windows.Forms.CheckBox();
      this.chkSm = new System.Windows.Forms.CheckBox();
      this.chkSc = new System.Windows.Forms.CheckBox();
      this.chkWl = new System.Windows.Forms.CheckBox();
      this.chkIl = new System.Windows.Forms.CheckBox();
      this.chkWi = new System.Windows.Forms.CheckBox();
      this.chkMg = new System.Windows.Forms.CheckBox();
      this.chkAl = new System.Windows.Forms.CheckBox();
      this.chkHl = new System.Windows.Forms.CheckBox();
      this.chkDc = new System.Windows.Forms.CheckBox();
      this.chkPr = new System.Windows.Forms.CheckBox();
      this.chkMo = new System.Windows.Forms.CheckBox();
      this.chkDr = new System.Windows.Forms.CheckBox();
      this.chkCl = new System.Windows.Forms.CheckBox();
      this.chkMc = new System.Windows.Forms.CheckBox();
      this.chkMe = new System.Windows.Forms.CheckBox();
      this.chkSb = new System.Windows.Forms.CheckBox();
      this.chkRa = new System.Windows.Forms.CheckBox();
      this.chkRg = new System.Windows.Forms.CheckBox();
      this.chkTh = new System.Windows.Forms.CheckBox();
      this.chkAp = new System.Windows.Forms.CheckBox();
      this.chkCr = new System.Windows.Forms.CheckBox();
      this.chkDk = new System.Windows.Forms.CheckBox();
      this.chkPa = new System.Windows.Forms.CheckBox();
      this.chkBr = new System.Windows.Forms.CheckBox();
      this.chkKn = new System.Windows.Forms.CheckBox();
      this.chkWa = new System.Windows.Forms.CheckBox();
      this.label6 = new System.Windows.Forms.Label();
      this.label7 = new System.Windows.Forms.Label();
      this.label8 = new System.Windows.Forms.Label();
      this.txtHP = new System.Windows.Forms.TextBox();
      this.txtMN = new System.Windows.Forms.TextBox();
      this.txtMV = new System.Windows.Forms.TextBox();
      this.txtReqWis = new System.Windows.Forms.TextBox();
      this.txtReqInt = new System.Windows.Forms.TextBox();
      this.txtReqCon = new System.Windows.Forms.TextBox();
      this.txtReqDex = new System.Windows.Forms.TextBox();
      this.txtReqStr = new System.Windows.Forms.TextBox();
      this.label19 = new System.Windows.Forms.Label();
      this.label20 = new System.Windows.Forms.Label();
      this.label21 = new System.Windows.Forms.Label();
      this.label22 = new System.Windows.Forms.Label();
      this.label23 = new System.Windows.Forms.Label();
      this.lblWorth = new System.Windows.Forms.Label();
      this.txtWorth = new System.Windows.Forms.TextBox();
      this.label16 = new System.Windows.Forms.Label();
      this.label17 = new System.Windows.Forms.Label();
      this.label18 = new System.Windows.Forms.Label();
      this.label24 = new System.Windows.Forms.Label();
      this.txtHit = new System.Windows.Forms.TextBox();
      this.txtDam = new System.Windows.Forms.TextBox();
      this.txtAC = new System.Windows.Forms.TextBox();
      this.txtRes = new System.Windows.Forms.TextBox();
      this.chkNeck = new System.Windows.Forms.CheckBox();
      this.chkRFinger = new System.Windows.Forms.CheckBox();
      this.chkLFinger = new System.Windows.Forms.CheckBox();
      this.chkRWrist = new System.Windows.Forms.CheckBox();
      this.chkLWrist = new System.Windows.Forms.CheckBox();
      this.chkREar = new System.Windows.Forms.CheckBox();
      this.chkLEar = new System.Windows.Forms.CheckBox();
      this.chkLegs = new System.Windows.Forms.CheckBox();
      this.chkWaist = new System.Windows.Forms.CheckBox();
      this.chkHands = new System.Windows.Forms.CheckBox();
      this.chkBack = new System.Windows.Forms.CheckBox();
      this.chkFeet = new System.Windows.Forms.CheckBox();
      this.chkHead = new System.Windows.Forms.CheckBox();
      this.chkBody = new System.Windows.Forms.CheckBox();
      this.chkRHand = new System.Windows.Forms.CheckBox();
      this.chkLHand = new System.Windows.Forms.CheckBox();
      this.chkShield = new System.Windows.Forms.CheckBox();
      this.chkWeapon = new System.Windows.Forms.CheckBox();
      this.label25 = new System.Windows.Forms.Label();
      this.txtBoardName = new System.Windows.Forms.TextBox();
      this.label26 = new System.Windows.Forms.Label();
      this.cmdSave = new System.Windows.Forms.Button();
      this.cmdCancel = new System.Windows.Forms.Button();
      this.label27 = new System.Windows.Forms.Label();
      this.txtMaxItems = new System.Windows.Forms.TextBox();
      this.label28 = new System.Windows.Forms.Label();
      this.label29 = new System.Windows.Forms.Label();
      this.txtLName = new System.Windows.Forms.TextBox();
      this.cboPotionSpells = new System.Windows.Forms.ComboBox();
      this.cmdAddSpell = new System.Windows.Forms.Button();
      this.cmdRemoveSpell = new System.Windows.Forms.Button();
      this.cboLoadIDs = new System.Windows.Forms.ComboBox();
      this.cmdAddID = new System.Windows.Forms.Button();
      this.cmdRemoveID = new System.Windows.Forms.Button();
      this.txtGroundDesc = new System.Windows.Forms.TextBox();
      this.label30 = new System.Windows.Forms.Label();
      this.label9 = new System.Windows.Forms.Label();
      this.label10 = new System.Windows.Forms.Label();
      this.label31 = new System.Windows.Forms.Label();
      this.label32 = new System.Windows.Forms.Label();
      this.label11 = new System.Windows.Forms.Label();
      this.chkEquipment = new System.Windows.Forms.CheckBox();
      this.chkEqContainer = new System.Windows.Forms.CheckBox();
      this.chkNormal = new System.Windows.Forms.CheckBox();
      this.chkContainer = new System.Windows.Forms.CheckBox();
      this.chkBoard = new System.Windows.Forms.CheckBox();
      this.chkFood = new System.Windows.Forms.CheckBox();
      this.chkDrink = new System.Windows.Forms.CheckBox();
      this.chkRefill = new System.Windows.Forms.CheckBox();
      this.chkScroll = new System.Windows.Forms.CheckBox();
      this.chkFountain = new System.Windows.Forms.CheckBox();
      this.cmdRemInnate = new System.Windows.Forms.Button();
      this.cmdAddInnate = new System.Windows.Forms.Button();
      this.cboInnate = new System.Windows.Forms.ComboBox();
      this.label12 = new System.Windows.Forms.Label();
      this.cmdRemoveCharged = new System.Windows.Forms.Button();
      this.cmdAddCharged = new System.Windows.Forms.Button();
      this.cboCharged = new System.Windows.Forms.ComboBox();
      this.label13 = new System.Windows.Forms.Label();
      this.txtCharges = new System.Windows.Forms.TextBox();
      this.label14 = new System.Windows.Forms.Label();
      this.label15 = new System.Windows.Forms.Label();
      this.chkStab = new System.Windows.Forms.CheckBox();
      this.chkBludgeon = new System.Windows.Forms.CheckBox();
      this.chkPierce = new System.Windows.Forms.CheckBox();
      this.chkSlash = new System.Windows.Forms.CheckBox();
      this.chkArrow = new System.Windows.Forms.CheckBox();
      this.chkStar = new System.Windows.Forms.CheckBox();
      this.chkWhip = new System.Windows.Forms.CheckBox();
      this.chkChop = new System.Windows.Forms.CheckBox();
      this.chkFist = new System.Windows.Forms.CheckBox();
      this.chkThrust = new System.Windows.Forms.CheckBox();
      this.chkGouge = new System.Windows.Forms.CheckBox();
      this.chkBlast = new System.Windows.Forms.CheckBox();
      this.chkPerish = new System.Windows.Forms.CheckBox();
      this.chkIngredient = new System.Windows.Forms.CheckBox();
      this.chkGlowing = new System.Windows.Forms.CheckBox();
      this.chkHumming = new System.Windows.Forms.CheckBox();
      this.chkNoGet = new System.Windows.Forms.CheckBox();
      this.chkNoSacrifice = new System.Windows.Forms.CheckBox();
      this.chkNoDrop = new System.Windows.Forms.CheckBox();
      this.chkBlocker = new System.Windows.Forms.CheckBox();
      this.label33 = new System.Windows.Forms.Label();
      this.label34 = new System.Windows.Forms.Label();
      this.txtBDir = new System.Windows.Forms.TextBox();
      this.txtBType = new System.Windows.Forms.TextBox();
      this.label35 = new System.Windows.Forms.Label();
      this.txtUses = new System.Windows.Forms.TextBox();
      this.label36 = new System.Windows.Forms.Label();
      this.txtLiquid = new System.Windows.Forms.TextBox();
      this.cmdSelectAll = new System.Windows.Forms.Button();
      this.chkEqLight = new System.Windows.Forms.CheckBox();
      this.label37 = new System.Windows.Forms.Label();
      this.txtLightTicks = new System.Windows.Forms.TextBox();
      this.chkInvisible = new System.Windows.Forms.CheckBox();
      this.chkUntouchable = new System.Windows.Forms.CheckBox();
      this.chkGood = new System.Windows.Forms.CheckBox();
      this.chkEvil = new System.Windows.Forms.CheckBox();
      this.chkNeutral = new System.Windows.Forms.CheckBox();
      this.chkBurning = new System.Windows.Forms.CheckBox();
      this.chkFreezing = new System.Windows.Forms.CheckBox();
      this.chkShocking = new System.Windows.Forms.CheckBox();
      this.chkNoRent = new System.Windows.Forms.CheckBox();
      this.cmdExact = new System.Windows.Forms.Button();
      this.chkExact = new System.Windows.Forms.CheckBox();
      this.SuspendLayout();
      // 
      // txtID
      // 
      this.txtID.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtID.Location = new System.Drawing.Point(64, 8);
      this.txtID.Name = "txtID";
      this.txtID.Size = new System.Drawing.Size(72, 20);
      this.txtID.TabIndex = 0;
      this.txtID.Text = "";
      // 
      // label1
      // 
      this.label1.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label1.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label1.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label1.Location = new System.Drawing.Point(8, 8);
      this.label1.Name = "label1";
      this.label1.Size = new System.Drawing.Size(48, 16);
      this.label1.TabIndex = 1;
      this.label1.Text = "ID:";
      this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label2
      // 
      this.label2.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label2.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label2.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label2.Location = new System.Drawing.Point(8, 32);
      this.label2.Name = "label2";
      this.label2.Size = new System.Drawing.Size(48, 16);
      this.label2.TabIndex = 2;
      this.label2.Text = "Name:";
      this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtName
      // 
      this.txtName.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtName.Location = new System.Drawing.Point(64, 32);
      this.txtName.Name = "txtName";
      this.txtName.Size = new System.Drawing.Size(296, 20);
      this.txtName.TabIndex = 1;
      this.txtName.Text = "";
      // 
      // label3
      // 
      this.label3.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label3.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label3.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label3.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label3.Location = new System.Drawing.Point(8, 116);
      this.label3.Name = "label3";
      this.label3.Size = new System.Drawing.Size(48, 16);
      this.label3.TabIndex = 4;
      this.label3.Text = "LDesc:";
      this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtLookAtDesc
      // 
      this.txtLookAtDesc.BackColor = System.Drawing.SystemColors.Window;
      this.txtLookAtDesc.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtLookAtDesc.Location = new System.Drawing.Point(64, 116);
      this.txtLookAtDesc.Multiline = true;
      this.txtLookAtDesc.Name = "txtLookAtDesc";
      this.txtLookAtDesc.Size = new System.Drawing.Size(296, 188);
      this.txtLookAtDesc.TabIndex = 4;
      this.txtLookAtDesc.Text = "";
      // 
      // label4
      // 
      this.label4.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label4.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label4.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label4.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label4.Location = new System.Drawing.Point(8, 80);
      this.label4.Name = "label4";
      this.label4.Size = new System.Drawing.Size(48, 16);
      this.label4.TabIndex = 6;
      this.label4.Text = "GDesc:";
      this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label5
      // 
      this.label5.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label5.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label5.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label5.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label5.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label5.Location = new System.Drawing.Point(552, 152);
      this.label5.Name = "label5";
      this.label5.Size = new System.Drawing.Size(32, 16);
      this.label5.TabIndex = 8;
      this.label5.Text = "Lvl";
      this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
      // 
      // txtLevel
      // 
      this.txtLevel.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtLevel.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtLevel.Location = new System.Drawing.Point(592, 152);
      this.txtLevel.Name = "txtLevel";
      this.txtLevel.Size = new System.Drawing.Size(56, 20);
      this.txtLevel.TabIndex = 10;
      this.txtLevel.Text = "";
      // 
      // chkAs
      // 
      this.chkAs.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkAs.BackgroundImage")));
      this.chkAs.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkAs.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkAs.Location = new System.Drawing.Point(208, 376);
      this.chkAs.Name = "chkAs";
      this.chkAs.Size = new System.Drawing.Size(40, 16);
      this.chkAs.TabIndex = 32;
      this.chkAs.Text = "As";
      // 
      // chkSh
      // 
      this.chkSh.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkSh.BackgroundImage")));
      this.chkSh.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkSh.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkSh.Location = new System.Drawing.Point(288, 456);
      this.chkSh.Name = "chkSh";
      this.chkSh.Size = new System.Drawing.Size(40, 16);
      this.chkSh.TabIndex = 51;
      this.chkSh.Text = "Sh";
      // 
      // chkSm
      // 
      this.chkSm.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkSm.BackgroundImage")));
      this.chkSm.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkSm.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkSm.Location = new System.Drawing.Point(288, 440);
      this.chkSm.Name = "chkSm";
      this.chkSm.Size = new System.Drawing.Size(40, 16);
      this.chkSm.TabIndex = 50;
      this.chkSm.Text = "Sm";
      // 
      // chkSc
      // 
      this.chkSc.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkSc.BackgroundImage")));
      this.chkSc.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkSc.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkSc.Location = new System.Drawing.Point(288, 424);
      this.chkSc.Name = "chkSc";
      this.chkSc.Size = new System.Drawing.Size(40, 16);
      this.chkSc.TabIndex = 49;
      this.chkSc.Text = "Sc";
      // 
      // chkWl
      // 
      this.chkWl.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkWl.BackgroundImage")));
      this.chkWl.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkWl.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkWl.Location = new System.Drawing.Point(288, 408);
      this.chkWl.Name = "chkWl";
      this.chkWl.Size = new System.Drawing.Size(40, 16);
      this.chkWl.TabIndex = 48;
      this.chkWl.Text = "Wl";
      // 
      // chkIl
      // 
      this.chkIl.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkIl.BackgroundImage")));
      this.chkIl.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkIl.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkIl.Location = new System.Drawing.Point(288, 392);
      this.chkIl.Name = "chkIl";
      this.chkIl.Size = new System.Drawing.Size(40, 16);
      this.chkIl.TabIndex = 47;
      this.chkIl.Text = "Il";
      // 
      // chkWi
      // 
      this.chkWi.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkWi.BackgroundImage")));
      this.chkWi.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkWi.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkWi.Location = new System.Drawing.Point(288, 376);
      this.chkWi.Name = "chkWi";
      this.chkWi.Size = new System.Drawing.Size(40, 16);
      this.chkWi.TabIndex = 46;
      this.chkWi.Text = "Wi";
      // 
      // chkMg
      // 
      this.chkMg.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkMg.BackgroundImage")));
      this.chkMg.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkMg.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkMg.Location = new System.Drawing.Point(288, 360);
      this.chkMg.Name = "chkMg";
      this.chkMg.Size = new System.Drawing.Size(40, 16);
      this.chkMg.TabIndex = 45;
      this.chkMg.Text = "Mg";
      // 
      // chkAl
      // 
      this.chkAl.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkAl.BackgroundImage")));
      this.chkAl.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkAl.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkAl.Location = new System.Drawing.Point(248, 456);
      this.chkAl.Name = "chkAl";
      this.chkAl.Size = new System.Drawing.Size(40, 16);
      this.chkAl.TabIndex = 44;
      this.chkAl.Text = "Al";
      // 
      // chkHl
      // 
      this.chkHl.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkHl.BackgroundImage")));
      this.chkHl.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkHl.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkHl.Location = new System.Drawing.Point(248, 440);
      this.chkHl.Name = "chkHl";
      this.chkHl.Size = new System.Drawing.Size(40, 16);
      this.chkHl.TabIndex = 43;
      this.chkHl.Text = "Hl";
      // 
      // chkDc
      // 
      this.chkDc.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkDc.BackgroundImage")));
      this.chkDc.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkDc.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkDc.Location = new System.Drawing.Point(248, 424);
      this.chkDc.Name = "chkDc";
      this.chkDc.Size = new System.Drawing.Size(40, 16);
      this.chkDc.TabIndex = 42;
      this.chkDc.Text = "Dc";
      // 
      // chkPr
      // 
      this.chkPr.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkPr.BackgroundImage")));
      this.chkPr.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkPr.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkPr.Location = new System.Drawing.Point(248, 408);
      this.chkPr.Name = "chkPr";
      this.chkPr.Size = new System.Drawing.Size(40, 16);
      this.chkPr.TabIndex = 41;
      this.chkPr.Text = "Pr";
      // 
      // chkMo
      // 
      this.chkMo.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkMo.BackgroundImage")));
      this.chkMo.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkMo.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkMo.Location = new System.Drawing.Point(248, 392);
      this.chkMo.Name = "chkMo";
      this.chkMo.Size = new System.Drawing.Size(40, 16);
      this.chkMo.TabIndex = 40;
      this.chkMo.Text = "Mo";
      // 
      // chkDr
      // 
      this.chkDr.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkDr.BackgroundImage")));
      this.chkDr.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkDr.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkDr.Location = new System.Drawing.Point(248, 376);
      this.chkDr.Name = "chkDr";
      this.chkDr.Size = new System.Drawing.Size(40, 16);
      this.chkDr.TabIndex = 39;
      this.chkDr.Text = "Dr";
      // 
      // chkCl
      // 
      this.chkCl.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkCl.BackgroundImage")));
      this.chkCl.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkCl.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkCl.Location = new System.Drawing.Point(248, 360);
      this.chkCl.Name = "chkCl";
      this.chkCl.Size = new System.Drawing.Size(40, 16);
      this.chkCl.TabIndex = 38;
      this.chkCl.Text = "Cl";
      // 
      // chkMc
      // 
      this.chkMc.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkMc.BackgroundImage")));
      this.chkMc.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkMc.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkMc.Location = new System.Drawing.Point(208, 456);
      this.chkMc.Name = "chkMc";
      this.chkMc.Size = new System.Drawing.Size(40, 16);
      this.chkMc.TabIndex = 37;
      this.chkMc.Text = "Mc";
      // 
      // chkMe
      // 
      this.chkMe.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkMe.BackgroundImage")));
      this.chkMe.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkMe.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkMe.Location = new System.Drawing.Point(208, 440);
      this.chkMe.Name = "chkMe";
      this.chkMe.Size = new System.Drawing.Size(40, 16);
      this.chkMe.TabIndex = 36;
      this.chkMe.Text = "Me";
      // 
      // chkSb
      // 
      this.chkSb.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkSb.BackgroundImage")));
      this.chkSb.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkSb.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkSb.Location = new System.Drawing.Point(208, 424);
      this.chkSb.Name = "chkSb";
      this.chkSb.Size = new System.Drawing.Size(40, 16);
      this.chkSb.TabIndex = 35;
      this.chkSb.Text = "Sb";
      // 
      // chkRa
      // 
      this.chkRa.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkRa.BackgroundImage")));
      this.chkRa.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkRa.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkRa.Location = new System.Drawing.Point(208, 408);
      this.chkRa.Name = "chkRa";
      this.chkRa.Size = new System.Drawing.Size(40, 16);
      this.chkRa.TabIndex = 34;
      this.chkRa.Text = "Ra";
      // 
      // chkRg
      // 
      this.chkRg.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkRg.BackgroundImage")));
      this.chkRg.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkRg.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkRg.Location = new System.Drawing.Point(208, 392);
      this.chkRg.Name = "chkRg";
      this.chkRg.Size = new System.Drawing.Size(40, 16);
      this.chkRg.TabIndex = 33;
      this.chkRg.Text = "Rg";
      // 
      // chkTh
      // 
      this.chkTh.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkTh.BackgroundImage")));
      this.chkTh.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkTh.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkTh.Location = new System.Drawing.Point(208, 360);
      this.chkTh.Name = "chkTh";
      this.chkTh.Size = new System.Drawing.Size(40, 16);
      this.chkTh.TabIndex = 31;
      this.chkTh.Text = "Th";
      // 
      // chkAp
      // 
      this.chkAp.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkAp.BackgroundImage")));
      this.chkAp.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkAp.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkAp.Location = new System.Drawing.Point(168, 456);
      this.chkAp.Name = "chkAp";
      this.chkAp.Size = new System.Drawing.Size(40, 16);
      this.chkAp.TabIndex = 30;
      this.chkAp.Text = "Ap";
      // 
      // chkCr
      // 
      this.chkCr.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkCr.BackgroundImage")));
      this.chkCr.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkCr.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkCr.Location = new System.Drawing.Point(168, 440);
      this.chkCr.Name = "chkCr";
      this.chkCr.Size = new System.Drawing.Size(40, 16);
      this.chkCr.TabIndex = 29;
      this.chkCr.Text = "Cr";
      // 
      // chkDk
      // 
      this.chkDk.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkDk.BackgroundImage")));
      this.chkDk.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkDk.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkDk.Location = new System.Drawing.Point(168, 424);
      this.chkDk.Name = "chkDk";
      this.chkDk.Size = new System.Drawing.Size(40, 16);
      this.chkDk.TabIndex = 28;
      this.chkDk.Text = "Dk";
      // 
      // chkPa
      // 
      this.chkPa.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkPa.BackgroundImage")));
      this.chkPa.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkPa.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkPa.Location = new System.Drawing.Point(168, 408);
      this.chkPa.Name = "chkPa";
      this.chkPa.Size = new System.Drawing.Size(40, 16);
      this.chkPa.TabIndex = 27;
      this.chkPa.Text = "Pa";
      // 
      // chkBr
      // 
      this.chkBr.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkBr.BackgroundImage")));
      this.chkBr.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkBr.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkBr.Location = new System.Drawing.Point(168, 392);
      this.chkBr.Name = "chkBr";
      this.chkBr.Size = new System.Drawing.Size(40, 16);
      this.chkBr.TabIndex = 26;
      this.chkBr.Text = "Br";
      // 
      // chkKn
      // 
      this.chkKn.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkKn.BackgroundImage")));
      this.chkKn.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkKn.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkKn.Location = new System.Drawing.Point(168, 376);
      this.chkKn.Name = "chkKn";
      this.chkKn.Size = new System.Drawing.Size(40, 16);
      this.chkKn.TabIndex = 25;
      this.chkKn.Text = "Kn";
      // 
      // chkWa
      // 
      this.chkWa.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkWa.BackgroundImage")));
      this.chkWa.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkWa.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkWa.Location = new System.Drawing.Point(168, 360);
      this.chkWa.Name = "chkWa";
      this.chkWa.Size = new System.Drawing.Size(40, 16);
      this.chkWa.TabIndex = 24;
      this.chkWa.Text = "Wa";
      // 
      // label6
      // 
      this.label6.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label6.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label6.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label6.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label6.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label6.Location = new System.Drawing.Point(368, 80);
      this.label6.Name = "label6";
      this.label6.Size = new System.Drawing.Size(32, 16);
      this.label6.TabIndex = 23;
      this.label6.Text = "HP";
      this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
      // 
      // label7
      // 
      this.label7.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label7.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label7.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label7.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label7.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label7.Location = new System.Drawing.Point(368, 32);
      this.label7.Name = "label7";
      this.label7.Size = new System.Drawing.Size(32, 16);
      this.label7.TabIndex = 24;
      this.label7.Text = "MN";
      this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
      // 
      // label8
      // 
      this.label8.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label8.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label8.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label8.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label8.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label8.Location = new System.Drawing.Point(368, 56);
      this.label8.Name = "label8";
      this.label8.Size = new System.Drawing.Size(32, 16);
      this.label8.TabIndex = 25;
      this.label8.Text = "MV";
      this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
      // 
      // txtHP
      // 
      this.txtHP.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtHP.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtHP.Location = new System.Drawing.Point(408, 80);
      this.txtHP.Name = "txtHP";
      this.txtHP.Size = new System.Drawing.Size(40, 20);
      this.txtHP.TabIndex = 26;
      this.txtHP.Text = "";
      // 
      // txtMN
      // 
      this.txtMN.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtMN.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtMN.Location = new System.Drawing.Point(408, 32);
      this.txtMN.Name = "txtMN";
      this.txtMN.Size = new System.Drawing.Size(40, 20);
      this.txtMN.TabIndex = 27;
      this.txtMN.Text = "";
      // 
      // txtMV
      // 
      this.txtMV.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtMV.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtMV.Location = new System.Drawing.Point(408, 56);
      this.txtMV.Name = "txtMV";
      this.txtMV.Size = new System.Drawing.Size(40, 20);
      this.txtMV.TabIndex = 28;
      this.txtMV.Text = "";
      // 
      // txtReqWis
      // 
      this.txtReqWis.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtReqWis.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtReqWis.Location = new System.Drawing.Point(592, 128);
      this.txtReqWis.Name = "txtReqWis";
      this.txtReqWis.Size = new System.Drawing.Size(56, 20);
      this.txtReqWis.TabIndex = 41;
      this.txtReqWis.Text = "";
      // 
      // txtReqInt
      // 
      this.txtReqInt.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtReqInt.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtReqInt.Location = new System.Drawing.Point(592, 104);
      this.txtReqInt.Name = "txtReqInt";
      this.txtReqInt.Size = new System.Drawing.Size(56, 20);
      this.txtReqInt.TabIndex = 40;
      this.txtReqInt.Text = "";
      // 
      // txtReqCon
      // 
      this.txtReqCon.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtReqCon.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtReqCon.Location = new System.Drawing.Point(592, 80);
      this.txtReqCon.Name = "txtReqCon";
      this.txtReqCon.Size = new System.Drawing.Size(56, 20);
      this.txtReqCon.TabIndex = 39;
      this.txtReqCon.Text = "";
      // 
      // txtReqDex
      // 
      this.txtReqDex.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtReqDex.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtReqDex.Location = new System.Drawing.Point(592, 56);
      this.txtReqDex.Name = "txtReqDex";
      this.txtReqDex.Size = new System.Drawing.Size(56, 20);
      this.txtReqDex.TabIndex = 38;
      this.txtReqDex.Text = "";
      // 
      // txtReqStr
      // 
      this.txtReqStr.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtReqStr.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtReqStr.Location = new System.Drawing.Point(592, 32);
      this.txtReqStr.Name = "txtReqStr";
      this.txtReqStr.Size = new System.Drawing.Size(56, 20);
      this.txtReqStr.TabIndex = 37;
      this.txtReqStr.Text = "";
      // 
      // label19
      // 
      this.label19.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label19.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label19.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label19.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label19.Location = new System.Drawing.Point(552, 128);
      this.label19.Name = "label19";
      this.label19.Size = new System.Drawing.Size(32, 16);
      this.label19.TabIndex = 36;
      this.label19.Text = "Wis";
      // 
      // label20
      // 
      this.label20.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label20.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label20.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label20.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label20.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label20.Location = new System.Drawing.Point(552, 104);
      this.label20.Name = "label20";
      this.label20.Size = new System.Drawing.Size(32, 16);
      this.label20.TabIndex = 35;
      this.label20.Text = "Int";
      // 
      // label21
      // 
      this.label21.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label21.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label21.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label21.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label21.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label21.Location = new System.Drawing.Point(552, 80);
      this.label21.Name = "label21";
      this.label21.Size = new System.Drawing.Size(32, 16);
      this.label21.TabIndex = 34;
      this.label21.Text = "Con";
      // 
      // label22
      // 
      this.label22.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label22.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label22.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label22.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label22.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label22.Location = new System.Drawing.Point(552, 56);
      this.label22.Name = "label22";
      this.label22.Size = new System.Drawing.Size(32, 16);
      this.label22.TabIndex = 33;
      this.label22.Text = "Dex";
      // 
      // label23
      // 
      this.label23.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label23.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label23.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label23.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label23.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label23.Location = new System.Drawing.Point(552, 32);
      this.label23.Name = "label23";
      this.label23.Size = new System.Drawing.Size(32, 16);
      this.label23.TabIndex = 32;
      this.label23.Text = "Str";
      // 
      // lblWorth
      // 
      this.lblWorth.BackColor = System.Drawing.Color.AntiqueWhite;
      this.lblWorth.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lblWorth.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.lblWorth.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.lblWorth.Location = new System.Drawing.Point(368, 136);
      this.lblWorth.Name = "lblWorth";
      this.lblWorth.Size = new System.Drawing.Size(88, 16);
      this.lblWorth.TabIndex = 42;
      this.lblWorth.Text = "Worth";
      this.lblWorth.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // txtWorth
      // 
      this.txtWorth.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtWorth.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtWorth.Location = new System.Drawing.Point(464, 136);
      this.txtWorth.Name = "txtWorth";
      this.txtWorth.Size = new System.Drawing.Size(72, 20);
      this.txtWorth.TabIndex = 43;
      this.txtWorth.Text = "";
      // 
      // label16
      // 
      this.label16.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label16.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label16.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label16.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label16.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label16.Location = new System.Drawing.Point(456, 32);
      this.label16.Name = "label16";
      this.label16.Size = new System.Drawing.Size(32, 16);
      this.label16.TabIndex = 44;
      this.label16.Text = "Hit";
      // 
      // label17
      // 
      this.label17.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label17.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label17.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label17.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label17.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label17.Location = new System.Drawing.Point(456, 56);
      this.label17.Name = "label17";
      this.label17.Size = new System.Drawing.Size(32, 16);
      this.label17.TabIndex = 45;
      this.label17.Text = "Dam";
      // 
      // label18
      // 
      this.label18.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label18.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label18.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label18.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label18.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label18.Location = new System.Drawing.Point(456, 80);
      this.label18.Name = "label18";
      this.label18.Size = new System.Drawing.Size(32, 16);
      this.label18.TabIndex = 46;
      this.label18.Text = "AC";
      this.label18.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
      // 
      // label24
      // 
      this.label24.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label24.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label24.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label24.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label24.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label24.Location = new System.Drawing.Point(456, 104);
      this.label24.Name = "label24";
      this.label24.Size = new System.Drawing.Size(32, 16);
      this.label24.TabIndex = 47;
      this.label24.Text = "Res";
      // 
      // txtHit
      // 
      this.txtHit.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtHit.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtHit.Location = new System.Drawing.Point(496, 32);
      this.txtHit.Name = "txtHit";
      this.txtHit.Size = new System.Drawing.Size(40, 20);
      this.txtHit.TabIndex = 48;
      this.txtHit.Text = "";
      // 
      // txtDam
      // 
      this.txtDam.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtDam.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtDam.Location = new System.Drawing.Point(496, 56);
      this.txtDam.Name = "txtDam";
      this.txtDam.Size = new System.Drawing.Size(40, 20);
      this.txtDam.TabIndex = 49;
      this.txtDam.Text = "";
      // 
      // txtAC
      // 
      this.txtAC.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtAC.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtAC.Location = new System.Drawing.Point(496, 80);
      this.txtAC.Name = "txtAC";
      this.txtAC.Size = new System.Drawing.Size(40, 20);
      this.txtAC.TabIndex = 50;
      this.txtAC.Text = "";
      // 
      // txtRes
      // 
      this.txtRes.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtRes.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtRes.Location = new System.Drawing.Point(496, 104);
      this.txtRes.Name = "txtRes";
      this.txtRes.Size = new System.Drawing.Size(40, 20);
      this.txtRes.TabIndex = 51;
      this.txtRes.Text = "";
      // 
      // chkNeck
      // 
      this.chkNeck.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNeck.BackgroundImage")));
      this.chkNeck.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNeck.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNeck.Location = new System.Drawing.Point(96, 488);
      this.chkNeck.Name = "chkNeck";
      this.chkNeck.Size = new System.Drawing.Size(88, 16);
      this.chkNeck.TabIndex = 23;
      this.chkNeck.Text = "Neck";
      // 
      // chkRFinger
      // 
      this.chkRFinger.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkRFinger.BackgroundImage")));
      this.chkRFinger.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkRFinger.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkRFinger.Location = new System.Drawing.Point(96, 472);
      this.chkRFinger.Name = "chkRFinger";
      this.chkRFinger.Size = new System.Drawing.Size(88, 16);
      this.chkRFinger.TabIndex = 22;
      this.chkRFinger.Text = "RFinger";
      // 
      // chkLFinger
      // 
      this.chkLFinger.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkLFinger.BackgroundImage")));
      this.chkLFinger.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkLFinger.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkLFinger.Location = new System.Drawing.Point(96, 456);
      this.chkLFinger.Name = "chkLFinger";
      this.chkLFinger.Size = new System.Drawing.Size(88, 16);
      this.chkLFinger.TabIndex = 21;
      this.chkLFinger.Text = "LFinger";
      // 
      // chkRWrist
      // 
      this.chkRWrist.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkRWrist.BackgroundImage")));
      this.chkRWrist.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkRWrist.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkRWrist.Location = new System.Drawing.Point(96, 440);
      this.chkRWrist.Name = "chkRWrist";
      this.chkRWrist.Size = new System.Drawing.Size(88, 16);
      this.chkRWrist.TabIndex = 20;
      this.chkRWrist.Text = "RWrist";
      // 
      // chkLWrist
      // 
      this.chkLWrist.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkLWrist.BackgroundImage")));
      this.chkLWrist.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkLWrist.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkLWrist.Location = new System.Drawing.Point(96, 424);
      this.chkLWrist.Name = "chkLWrist";
      this.chkLWrist.Size = new System.Drawing.Size(88, 16);
      this.chkLWrist.TabIndex = 19;
      this.chkLWrist.Text = "LWrist";
      // 
      // chkREar
      // 
      this.chkREar.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkREar.BackgroundImage")));
      this.chkREar.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkREar.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkREar.Location = new System.Drawing.Point(96, 408);
      this.chkREar.Name = "chkREar";
      this.chkREar.Size = new System.Drawing.Size(88, 16);
      this.chkREar.TabIndex = 18;
      this.chkREar.Text = "REar";
      // 
      // chkLEar
      // 
      this.chkLEar.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkLEar.BackgroundImage")));
      this.chkLEar.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkLEar.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkLEar.Location = new System.Drawing.Point(96, 392);
      this.chkLEar.Name = "chkLEar";
      this.chkLEar.Size = new System.Drawing.Size(80, 16);
      this.chkLEar.TabIndex = 17;
      this.chkLEar.Text = "LEar";
      // 
      // chkLegs
      // 
      this.chkLegs.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkLegs.BackgroundImage")));
      this.chkLegs.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkLegs.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkLegs.Location = new System.Drawing.Point(96, 376);
      this.chkLegs.Name = "chkLegs";
      this.chkLegs.Size = new System.Drawing.Size(96, 16);
      this.chkLegs.TabIndex = 16;
      this.chkLegs.Text = "Legs";
      // 
      // chkWaist
      // 
      this.chkWaist.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkWaist.BackgroundImage")));
      this.chkWaist.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkWaist.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkWaist.Location = new System.Drawing.Point(96, 360);
      this.chkWaist.Name = "chkWaist";
      this.chkWaist.Size = new System.Drawing.Size(96, 16);
      this.chkWaist.TabIndex = 15;
      this.chkWaist.Text = "Waist";
      // 
      // chkHands
      // 
      this.chkHands.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkHands.BackgroundImage")));
      this.chkHands.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkHands.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkHands.Location = new System.Drawing.Point(8, 488);
      this.chkHands.Name = "chkHands";
      this.chkHands.Size = new System.Drawing.Size(80, 16);
      this.chkHands.TabIndex = 14;
      this.chkHands.Text = "Hands";
      // 
      // chkBack
      // 
      this.chkBack.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkBack.BackgroundImage")));
      this.chkBack.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkBack.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkBack.Location = new System.Drawing.Point(8, 472);
      this.chkBack.Name = "chkBack";
      this.chkBack.Size = new System.Drawing.Size(80, 16);
      this.chkBack.TabIndex = 13;
      this.chkBack.Text = "Back";
      // 
      // chkFeet
      // 
      this.chkFeet.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkFeet.BackgroundImage")));
      this.chkFeet.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkFeet.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkFeet.Location = new System.Drawing.Point(8, 456);
      this.chkFeet.Name = "chkFeet";
      this.chkFeet.Size = new System.Drawing.Size(80, 16);
      this.chkFeet.TabIndex = 12;
      this.chkFeet.Text = "Feet";
      // 
      // chkHead
      // 
      this.chkHead.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkHead.BackgroundImage")));
      this.chkHead.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkHead.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkHead.Location = new System.Drawing.Point(8, 440);
      this.chkHead.Name = "chkHead";
      this.chkHead.Size = new System.Drawing.Size(80, 16);
      this.chkHead.TabIndex = 11;
      this.chkHead.Text = "Head";
      // 
      // chkBody
      // 
      this.chkBody.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkBody.BackgroundImage")));
      this.chkBody.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkBody.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkBody.Location = new System.Drawing.Point(8, 424);
      this.chkBody.Name = "chkBody";
      this.chkBody.Size = new System.Drawing.Size(88, 16);
      this.chkBody.TabIndex = 10;
      this.chkBody.Text = "Body";
      // 
      // chkRHand
      // 
      this.chkRHand.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkRHand.BackgroundImage")));
      this.chkRHand.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkRHand.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkRHand.Location = new System.Drawing.Point(8, 408);
      this.chkRHand.Name = "chkRHand";
      this.chkRHand.Size = new System.Drawing.Size(96, 16);
      this.chkRHand.TabIndex = 9;
      this.chkRHand.Text = "RHand";
      // 
      // chkLHand
      // 
      this.chkLHand.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkLHand.BackgroundImage")));
      this.chkLHand.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkLHand.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkLHand.Location = new System.Drawing.Point(8, 392);
      this.chkLHand.Name = "chkLHand";
      this.chkLHand.Size = new System.Drawing.Size(88, 16);
      this.chkLHand.TabIndex = 8;
      this.chkLHand.Text = "LHand";
      // 
      // chkShield
      // 
      this.chkShield.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkShield.BackgroundImage")));
      this.chkShield.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkShield.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkShield.Location = new System.Drawing.Point(8, 376);
      this.chkShield.Name = "chkShield";
      this.chkShield.Size = new System.Drawing.Size(72, 16);
      this.chkShield.TabIndex = 7;
      this.chkShield.Text = "Shield";
      // 
      // chkWeapon
      // 
      this.chkWeapon.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkWeapon.BackgroundImage")));
      this.chkWeapon.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkWeapon.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkWeapon.Location = new System.Drawing.Point(8, 360);
      this.chkWeapon.Name = "chkWeapon";
      this.chkWeapon.Size = new System.Drawing.Size(72, 16);
      this.chkWeapon.TabIndex = 6;
      this.chkWeapon.Text = "Weapon";
      // 
      // label25
      // 
      this.label25.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label25.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label25.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label25.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label25.Location = new System.Drawing.Point(368, 256);
      this.label25.Name = "label25";
      this.label25.Size = new System.Drawing.Size(88, 16);
      this.label25.TabIndex = 53;
      this.label25.Text = "Board Name";
      this.label25.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // txtBoardName
      // 
      this.txtBoardName.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtBoardName.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtBoardName.Location = new System.Drawing.Point(464, 256);
      this.txtBoardName.Name = "txtBoardName";
      this.txtBoardName.Size = new System.Drawing.Size(192, 20);
      this.txtBoardName.TabIndex = 54;
      this.txtBoardName.Text = "";
      // 
      // label26
      // 
      this.label26.BackColor = System.Drawing.Color.Lavender;
      this.label26.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label26.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label26.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label26.Location = new System.Drawing.Point(520, 416);
      this.label26.Name = "label26";
      this.label26.Size = new System.Drawing.Size(136, 16);
      this.label26.TabIndex = 55;
      this.label26.Text = "Consumable Spells";
      this.label26.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // cmdSave
      // 
      this.cmdSave.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdSave.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdSave.Location = new System.Drawing.Point(230, 648);
      this.cmdSave.Name = "cmdSave";
      this.cmdSave.TabIndex = 57;
      this.cmdSave.Text = "Save";
      this.cmdSave.Click += new System.EventHandler(this.cmdSave_Click);
      // 
      // cmdCancel
      // 
      this.cmdCancel.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdCancel.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdCancel.Location = new System.Drawing.Point(344, 648);
      this.cmdCancel.Name = "cmdCancel";
      this.cmdCancel.TabIndex = 58;
      this.cmdCancel.Text = "Cancel";
      this.cmdCancel.Click += new System.EventHandler(this.cmdCancel_Click);
      // 
      // label27
      // 
      this.label27.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label27.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label27.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label27.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label27.Location = new System.Drawing.Point(368, 160);
      this.label27.Name = "label27";
      this.label27.Size = new System.Drawing.Size(88, 16);
      this.label27.TabIndex = 59;
      this.label27.Text = "Max Items";
      this.label27.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // txtMaxItems
      // 
      this.txtMaxItems.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtMaxItems.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtMaxItems.Location = new System.Drawing.Point(464, 160);
      this.txtMaxItems.Name = "txtMaxItems";
      this.txtMaxItems.Size = new System.Drawing.Size(72, 20);
      this.txtMaxItems.TabIndex = 60;
      this.txtMaxItems.Text = "";
      // 
      // label28
      // 
      this.label28.BackColor = System.Drawing.Color.Lavender;
      this.label28.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label28.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label28.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label28.Location = new System.Drawing.Point(520, 336);
      this.label28.Name = "label28";
      this.label28.Size = new System.Drawing.Size(136, 16);
      this.label28.TabIndex = 62;
      this.label28.Text = "Contained Items";
      this.label28.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label29
      // 
      this.label29.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label29.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label29.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label29.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label29.Location = new System.Drawing.Point(8, 56);
      this.label29.Name = "label29";
      this.label29.Size = new System.Drawing.Size(48, 16);
      this.label29.TabIndex = 63;
      this.label29.Text = "LName:";
      this.label29.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtLName
      // 
      this.txtLName.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtLName.Location = new System.Drawing.Point(64, 56);
      this.txtLName.Name = "txtLName";
      this.txtLName.Size = new System.Drawing.Size(296, 20);
      this.txtLName.TabIndex = 2;
      this.txtLName.Text = "";
      // 
      // cboPotionSpells
      // 
      this.cboPotionSpells.Location = new System.Drawing.Point(520, 440);
      this.cboPotionSpells.Name = "cboPotionSpells";
      this.cboPotionSpells.Size = new System.Drawing.Size(136, 22);
      this.cboPotionSpells.TabIndex = 65;
      // 
      // cmdAddSpell
      // 
      this.cmdAddSpell.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddSpell.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddSpell.Location = new System.Drawing.Point(520, 464);
      this.cmdAddSpell.Name = "cmdAddSpell";
      this.cmdAddSpell.Size = new System.Drawing.Size(56, 23);
      this.cmdAddSpell.TabIndex = 66;
      this.cmdAddSpell.Text = "Add";
      this.cmdAddSpell.Click += new System.EventHandler(this.cmdAddSpell_Click);
      // 
      // cmdRemoveSpell
      // 
      this.cmdRemoveSpell.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveSpell.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveSpell.Location = new System.Drawing.Point(600, 464);
      this.cmdRemoveSpell.Name = "cmdRemoveSpell";
      this.cmdRemoveSpell.Size = new System.Drawing.Size(56, 23);
      this.cmdRemoveSpell.TabIndex = 67;
      this.cmdRemoveSpell.Text = "Remove";
      this.cmdRemoveSpell.Click += new System.EventHandler(this.cmdRemoveSpell_Click);
      // 
      // cboLoadIDs
      // 
      this.cboLoadIDs.Location = new System.Drawing.Point(520, 360);
      this.cboLoadIDs.Name = "cboLoadIDs";
      this.cboLoadIDs.Size = new System.Drawing.Size(136, 22);
      this.cboLoadIDs.TabIndex = 68;
      // 
      // cmdAddID
      // 
      this.cmdAddID.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddID.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddID.Location = new System.Drawing.Point(520, 384);
      this.cmdAddID.Name = "cmdAddID";
      this.cmdAddID.Size = new System.Drawing.Size(56, 23);
      this.cmdAddID.TabIndex = 69;
      this.cmdAddID.Text = "Add";
      this.cmdAddID.Click += new System.EventHandler(this.cmdAddID_Click);
      // 
      // cmdRemoveID
      // 
      this.cmdRemoveID.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveID.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveID.Location = new System.Drawing.Point(600, 384);
      this.cmdRemoveID.Name = "cmdRemoveID";
      this.cmdRemoveID.Size = new System.Drawing.Size(56, 23);
      this.cmdRemoveID.TabIndex = 70;
      this.cmdRemoveID.Text = "Remove";
      this.cmdRemoveID.Click += new System.EventHandler(this.cmdRemoveID_Click);
      // 
      // txtGroundDesc
      // 
      this.txtGroundDesc.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtGroundDesc.Location = new System.Drawing.Point(64, 80);
      this.txtGroundDesc.Multiline = true;
      this.txtGroundDesc.Name = "txtGroundDesc";
      this.txtGroundDesc.Size = new System.Drawing.Size(296, 32);
      this.txtGroundDesc.TabIndex = 3;
      this.txtGroundDesc.Text = "";
      // 
      // label30
      // 
      this.label30.BackColor = System.Drawing.Color.Lavender;
      this.label30.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label30.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label30.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label30.Location = new System.Drawing.Point(328, 336);
      this.label30.Name = "label30";
      this.label30.Size = new System.Drawing.Size(184, 16);
      this.label30.TabIndex = 71;
      this.label30.Text = "Flags";
      this.label30.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label9
      // 
      this.label9.BackColor = System.Drawing.Color.Lavender;
      this.label9.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label9.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label9.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label9.Location = new System.Drawing.Point(368, 8);
      this.label9.Name = "label9";
      this.label9.Size = new System.Drawing.Size(168, 16);
      this.label9.TabIndex = 72;
      this.label9.Text = "Bonuses";
      this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label10
      // 
      this.label10.BackColor = System.Drawing.Color.Lavender;
      this.label10.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label10.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label10.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label10.Location = new System.Drawing.Point(552, 8);
      this.label10.Name = "label10";
      this.label10.Size = new System.Drawing.Size(96, 16);
      this.label10.TabIndex = 73;
      this.label10.Text = "Requirements";
      this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label31
      // 
      this.label31.BackColor = System.Drawing.Color.Lavender;
      this.label31.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label31.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label31.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label31.Location = new System.Drawing.Point(168, 336);
      this.label31.Name = "label31";
      this.label31.Size = new System.Drawing.Size(152, 16);
      this.label31.TabIndex = 74;
      this.label31.Text = "Classes";
      this.label31.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label32
      // 
      this.label32.BackColor = System.Drawing.Color.Lavender;
      this.label32.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label32.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label32.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label32.Location = new System.Drawing.Point(8, 336);
      this.label32.Name = "label32";
      this.label32.Size = new System.Drawing.Size(152, 16);
      this.label32.TabIndex = 75;
      this.label32.Text = "Places";
      this.label32.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label11
      // 
      this.label11.BackColor = System.Drawing.Color.Lavender;
      this.label11.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label11.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label11.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label11.Location = new System.Drawing.Point(8, 512);
      this.label11.Name = "label11";
      this.label11.Size = new System.Drawing.Size(256, 16);
      this.label11.TabIndex = 76;
      this.label11.Text = "Dominant Item Type";
      this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // chkEquipment
      // 
      this.chkEquipment.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkEquipment.BackgroundImage")));
      this.chkEquipment.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkEquipment.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkEquipment.Location = new System.Drawing.Point(96, 536);
      this.chkEquipment.Name = "chkEquipment";
      this.chkEquipment.Size = new System.Drawing.Size(88, 16);
      this.chkEquipment.TabIndex = 59;
      this.chkEquipment.Text = "Equipment";
      this.chkEquipment.Click += new System.EventHandler(this.chkEquipment_CheckedChanged);
      // 
      // chkEqContainer
      // 
      this.chkEqContainer.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkEqContainer.BackgroundImage")));
      this.chkEqContainer.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkEqContainer.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkEqContainer.Location = new System.Drawing.Point(96, 552);
      this.chkEqContainer.Name = "chkEqContainer";
      this.chkEqContainer.Size = new System.Drawing.Size(152, 16);
      this.chkEqContainer.TabIndex = 60;
      this.chkEqContainer.Text = "Equipable Container";
      this.chkEqContainer.Click += new System.EventHandler(this.chkEqContainer_CheckedChanged);
      // 
      // chkNormal
      // 
      this.chkNormal.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNormal.BackgroundImage")));
      this.chkNormal.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNormal.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNormal.Location = new System.Drawing.Point(8, 536);
      this.chkNormal.Name = "chkNormal";
      this.chkNormal.Size = new System.Drawing.Size(64, 16);
      this.chkNormal.TabIndex = 53;
      this.chkNormal.Text = "Normal";
      // 
      // chkContainer
      // 
      this.chkContainer.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkContainer.BackgroundImage")));
      this.chkContainer.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkContainer.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkContainer.Location = new System.Drawing.Point(8, 552);
      this.chkContainer.Name = "chkContainer";
      this.chkContainer.Size = new System.Drawing.Size(88, 16);
      this.chkContainer.TabIndex = 54;
      this.chkContainer.Text = "Container";
      this.chkContainer.Click += new System.EventHandler(this.chkContainer_CheckedChanged);
      // 
      // chkBoard
      // 
      this.chkBoard.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkBoard.BackgroundImage")));
      this.chkBoard.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkBoard.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkBoard.Location = new System.Drawing.Point(8, 568);
      this.chkBoard.Name = "chkBoard";
      this.chkBoard.Size = new System.Drawing.Size(56, 16);
      this.chkBoard.TabIndex = 55;
      this.chkBoard.Text = "Board";
      this.chkBoard.Click += new System.EventHandler(this.chkBoard_CheckedChanged);
      // 
      // chkFood
      // 
      this.chkFood.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkFood.BackgroundImage")));
      this.chkFood.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkFood.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkFood.Location = new System.Drawing.Point(96, 616);
      this.chkFood.Name = "chkFood";
      this.chkFood.Size = new System.Drawing.Size(128, 16);
      this.chkFood.TabIndex = 64;
      this.chkFood.Text = "Consumable Food";
      // 
      // chkDrink
      // 
      this.chkDrink.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkDrink.BackgroundImage")));
      this.chkDrink.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkDrink.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkDrink.Location = new System.Drawing.Point(96, 584);
      this.chkDrink.Name = "chkDrink";
      this.chkDrink.Size = new System.Drawing.Size(136, 16);
      this.chkDrink.TabIndex = 62;
      this.chkDrink.Text = "Consumable Drink";
      this.chkDrink.CheckedChanged += new EventHandler(this.Refill_CheckedChanged);
      // 
      // chkRefill
      // 
      this.chkRefill.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkRefill.BackgroundImage")));
      this.chkRefill.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkRefill.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkRefill.Location = new System.Drawing.Point(96, 600);
      this.chkRefill.Name = "chkRefill";
      this.chkRefill.Size = new System.Drawing.Size(136, 16);
      this.chkRefill.TabIndex = 63;
      this.chkRefill.Text = "Liquid Container";
      this.chkRefill.Click += new System.EventHandler(this.Refill_CheckedChanged);
      // 
      // chkScroll
      // 
      this.chkScroll.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkScroll.BackgroundImage")));
      this.chkScroll.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkScroll.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkScroll.Location = new System.Drawing.Point(8, 616);
      this.chkScroll.Name = "chkScroll";
      this.chkScroll.Size = new System.Drawing.Size(64, 16);
      this.chkScroll.TabIndex = 58;
      this.chkScroll.Text = "Scroll";
      this.chkScroll.Click += new System.EventHandler(this.Consumable_CheckedChanged);
      // 
      // chkFountain
      // 
      this.chkFountain.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkFountain.BackgroundImage")));
      this.chkFountain.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkFountain.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkFountain.Location = new System.Drawing.Point(8, 584);
      this.chkFountain.Name = "chkFountain";
      this.chkFountain.Size = new System.Drawing.Size(80, 16);
      this.chkFountain.TabIndex = 56;
      this.chkFountain.Text = "Fountain";
      this.chkFountain.Click += new System.EventHandler(this.chkFountain_CheckedChanged);
      // 
      // cmdRemInnate
      // 
      this.cmdRemInnate.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemInnate.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemInnate.Location = new System.Drawing.Point(600, 544);
      this.cmdRemInnate.Name = "cmdRemInnate";
      this.cmdRemInnate.Size = new System.Drawing.Size(56, 23);
      this.cmdRemInnate.TabIndex = 93;
      this.cmdRemInnate.Text = "Remove";
      this.cmdRemInnate.Click += new System.EventHandler(this.cmdRemInnate_Click);
      // 
      // cmdAddInnate
      // 
      this.cmdAddInnate.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddInnate.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddInnate.Location = new System.Drawing.Point(520, 544);
      this.cmdAddInnate.Name = "cmdAddInnate";
      this.cmdAddInnate.Size = new System.Drawing.Size(56, 23);
      this.cmdAddInnate.TabIndex = 92;
      this.cmdAddInnate.Text = "Add";
      this.cmdAddInnate.Click += new System.EventHandler(this.cmdAddInnate_Click);
      // 
      // cboInnate
      // 
      this.cboInnate.Location = new System.Drawing.Point(520, 520);
      this.cboInnate.Name = "cboInnate";
      this.cboInnate.Size = new System.Drawing.Size(136, 22);
      this.cboInnate.TabIndex = 91;
      // 
      // label12
      // 
      this.label12.BackColor = System.Drawing.Color.Lavender;
      this.label12.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label12.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label12.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label12.Location = new System.Drawing.Point(520, 496);
      this.label12.Name = "label12";
      this.label12.Size = new System.Drawing.Size(136, 16);
      this.label12.TabIndex = 90;
      this.label12.Text = "Innate Spells";
      this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // cmdRemoveCharged
      // 
      this.cmdRemoveCharged.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveCharged.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveCharged.Location = new System.Drawing.Point(600, 624);
      this.cmdRemoveCharged.Name = "cmdRemoveCharged";
      this.cmdRemoveCharged.Size = new System.Drawing.Size(56, 23);
      this.cmdRemoveCharged.TabIndex = 97;
      this.cmdRemoveCharged.Text = "Remove";
      this.cmdRemoveCharged.Click += new System.EventHandler(this.cmdRemoveCharged_Click);
      // 
      // cmdAddCharged
      // 
      this.cmdAddCharged.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddCharged.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddCharged.Location = new System.Drawing.Point(520, 624);
      this.cmdAddCharged.Name = "cmdAddCharged";
      this.cmdAddCharged.Size = new System.Drawing.Size(56, 23);
      this.cmdAddCharged.TabIndex = 96;
      this.cmdAddCharged.Text = "Add";
      this.cmdAddCharged.Click += new System.EventHandler(this.cmdAddCharged_Click);
      // 
      // cboCharged
      // 
      this.cboCharged.Location = new System.Drawing.Point(520, 600);
      this.cboCharged.Name = "cboCharged";
      this.cboCharged.Size = new System.Drawing.Size(136, 22);
      this.cboCharged.TabIndex = 95;
      // 
      // label13
      // 
      this.label13.BackColor = System.Drawing.Color.Lavender;
      this.label13.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label13.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label13.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label13.Location = new System.Drawing.Point(520, 576);
      this.label13.Name = "label13";
      this.label13.Size = new System.Drawing.Size(136, 16);
      this.label13.TabIndex = 94;
      this.label13.Text = "Charged Spells";
      this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // txtCharges
      // 
      this.txtCharges.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtCharges.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtCharges.Location = new System.Drawing.Point(592, 656);
      this.txtCharges.Name = "txtCharges";
      this.txtCharges.Size = new System.Drawing.Size(64, 20);
      this.txtCharges.TabIndex = 99;
      this.txtCharges.Text = "";
      // 
      // label14
      // 
      this.label14.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label14.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label14.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label14.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label14.Location = new System.Drawing.Point(520, 656);
      this.label14.Name = "label14";
      this.label14.Size = new System.Drawing.Size(64, 16);
      this.label14.TabIndex = 98;
      this.label14.Text = "Charges:";
      this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label15
      // 
      this.label15.BackColor = System.Drawing.Color.Lavender;
      this.label15.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label15.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label15.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label15.Location = new System.Drawing.Point(272, 512);
      this.label15.Name = "label15";
      this.label15.Size = new System.Drawing.Size(224, 16);
      this.label15.TabIndex = 100;
      this.label15.Text = "Damage Type (Weapon)";
      this.label15.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // chkStab
      // 
      this.chkStab.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkStab.BackgroundImage")));
      this.chkStab.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkStab.Location = new System.Drawing.Point(272, 536);
      this.chkStab.Name = "chkStab";
      this.chkStab.Size = new System.Drawing.Size(56, 16);
      this.chkStab.TabIndex = 101;
      this.chkStab.Text = "Stab";
      // 
      // chkBludgeon
      // 
      this.chkBludgeon.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkBludgeon.BackgroundImage")));
      this.chkBludgeon.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkBludgeon.Location = new System.Drawing.Point(272, 584);
      this.chkBludgeon.Name = "chkBludgeon";
      this.chkBludgeon.Size = new System.Drawing.Size(80, 16);
      this.chkBludgeon.TabIndex = 102;
      this.chkBludgeon.Text = "Bludgeon";
      // 
      // chkPierce
      // 
      this.chkPierce.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkPierce.BackgroundImage")));
      this.chkPierce.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkPierce.Location = new System.Drawing.Point(272, 568);
      this.chkPierce.Name = "chkPierce";
      this.chkPierce.Size = new System.Drawing.Size(64, 16);
      this.chkPierce.TabIndex = 103;
      this.chkPierce.Text = "Pierce";
      // 
      // chkSlash
      // 
      this.chkSlash.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkSlash.BackgroundImage")));
      this.chkSlash.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkSlash.Location = new System.Drawing.Point(272, 552);
      this.chkSlash.Name = "chkSlash";
      this.chkSlash.Size = new System.Drawing.Size(56, 16);
      this.chkSlash.TabIndex = 104;
      this.chkSlash.Text = "Slash";
      // 
      // chkArrow
      // 
      this.chkArrow.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkArrow.BackgroundImage")));
      this.chkArrow.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkArrow.Location = new System.Drawing.Point(352, 568);
      this.chkArrow.Name = "chkArrow";
      this.chkArrow.Size = new System.Drawing.Size(56, 16);
      this.chkArrow.TabIndex = 105;
      this.chkArrow.Text = "Arrow";
      // 
      // chkStar
      // 
      this.chkStar.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkStar.BackgroundImage")));
      this.chkStar.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkStar.Location = new System.Drawing.Point(352, 584);
      this.chkStar.Name = "chkStar";
      this.chkStar.Size = new System.Drawing.Size(48, 16);
      this.chkStar.TabIndex = 106;
      this.chkStar.Text = "Star";
      // 
      // chkWhip
      // 
      this.chkWhip.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkWhip.BackgroundImage")));
      this.chkWhip.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkWhip.Location = new System.Drawing.Point(352, 552);
      this.chkWhip.Name = "chkWhip";
      this.chkWhip.Size = new System.Drawing.Size(48, 16);
      this.chkWhip.TabIndex = 107;
      this.chkWhip.Text = "Whip";
      // 
      // chkChop
      // 
      this.chkChop.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkChop.BackgroundImage")));
      this.chkChop.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkChop.Location = new System.Drawing.Point(352, 536);
      this.chkChop.Name = "chkChop";
      this.chkChop.Size = new System.Drawing.Size(48, 16);
      this.chkChop.TabIndex = 108;
      this.chkChop.Text = "Chop";
      // 
      // chkFist
      // 
      this.chkFist.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkFist.BackgroundImage")));
      this.chkFist.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkFist.Location = new System.Drawing.Point(416, 536);
      this.chkFist.Name = "chkFist";
      this.chkFist.Size = new System.Drawing.Size(48, 16);
      this.chkFist.TabIndex = 109;
      this.chkFist.Text = "Fist";
      // 
      // chkThrust
      // 
      this.chkThrust.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkThrust.BackgroundImage")));
      this.chkThrust.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkThrust.Location = new System.Drawing.Point(416, 552);
      this.chkThrust.Name = "chkThrust";
      this.chkThrust.Size = new System.Drawing.Size(64, 16);
      this.chkThrust.TabIndex = 110;
      this.chkThrust.Text = "Thrust";
      // 
      // chkGouge
      // 
      this.chkGouge.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkGouge.BackgroundImage")));
      this.chkGouge.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkGouge.Location = new System.Drawing.Point(416, 568);
      this.chkGouge.Name = "chkGouge";
      this.chkGouge.Size = new System.Drawing.Size(56, 16);
      this.chkGouge.TabIndex = 111;
      this.chkGouge.Text = "Gouge";
      // 
      // chkBlast
      // 
      this.chkBlast.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkBlast.BackgroundImage")));
      this.chkBlast.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkBlast.Location = new System.Drawing.Point(416, 584);
      this.chkBlast.Name = "chkBlast";
      this.chkBlast.Size = new System.Drawing.Size(56, 16);
      this.chkBlast.TabIndex = 112;
      this.chkBlast.Text = "Blast";
      // 
      // chkPerish
      // 
      this.chkPerish.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkPerish.BackgroundImage")));
      this.chkPerish.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkPerish.Location = new System.Drawing.Point(416, 360);
      this.chkPerish.Name = "chkPerish";
      this.chkPerish.Size = new System.Drawing.Size(72, 16);
      this.chkPerish.TabIndex = 113;
      this.chkPerish.Text = "PERISH";
      // 
      // chkIngredient
      // 
      this.chkIngredient.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkIngredient.BackgroundImage")));
      this.chkIngredient.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkIngredient.Location = new System.Drawing.Point(416, 376);
      this.chkIngredient.Name = "chkIngredient";
      this.chkIngredient.Size = new System.Drawing.Size(88, 16);
      this.chkIngredient.TabIndex = 114;
      this.chkIngredient.Text = "INGREDIENT";
      // 
      // chkGlowing
      // 
      this.chkGlowing.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkGlowing.BackgroundImage")));
      this.chkGlowing.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkGlowing.Location = new System.Drawing.Point(328, 360);
      this.chkGlowing.Name = "chkGlowing";
      this.chkGlowing.Size = new System.Drawing.Size(72, 16);
      this.chkGlowing.TabIndex = 115;
      this.chkGlowing.Text = "GLOWING";
      // 
      // chkHumming
      // 
      this.chkHumming.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkHumming.BackgroundImage")));
      this.chkHumming.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkHumming.Location = new System.Drawing.Point(328, 376);
      this.chkHumming.Name = "chkHumming";
      this.chkHumming.Size = new System.Drawing.Size(72, 16);
      this.chkHumming.TabIndex = 116;
      this.chkHumming.Text = "HUMMING";
      // 
      // chkNoGet
      // 
      this.chkNoGet.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNoGet.BackgroundImage")));
      this.chkNoGet.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNoGet.Location = new System.Drawing.Point(416, 424);
      this.chkNoGet.Name = "chkNoGet";
      this.chkNoGet.Size = new System.Drawing.Size(72, 16);
      this.chkNoGet.TabIndex = 117;
      this.chkNoGet.Text = "NO_GET";
      // 
      // chkNoSacrifice
      // 
      this.chkNoSacrifice.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNoSacrifice.BackgroundImage")));
      this.chkNoSacrifice.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNoSacrifice.Location = new System.Drawing.Point(416, 456);
      this.chkNoSacrifice.Name = "chkNoSacrifice";
      this.chkNoSacrifice.Size = new System.Drawing.Size(104, 16);
      this.chkNoSacrifice.TabIndex = 118;
      this.chkNoSacrifice.Text = "NO_SACRIFICE";
      // 
      // chkNoDrop
      // 
      this.chkNoDrop.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNoDrop.BackgroundImage")));
      this.chkNoDrop.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNoDrop.Location = new System.Drawing.Point(416, 440);
      this.chkNoDrop.Name = "chkNoDrop";
      this.chkNoDrop.Size = new System.Drawing.Size(72, 16);
      this.chkNoDrop.TabIndex = 119;
      this.chkNoDrop.Text = "NO_DROP";
      // 
      // chkBlocker
      // 
      this.chkBlocker.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkBlocker.BackgroundImage")));
      this.chkBlocker.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkBlocker.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkBlocker.Location = new System.Drawing.Point(8, 600);
      this.chkBlocker.Name = "chkBlocker";
      this.chkBlocker.Size = new System.Drawing.Size(80, 16);
      this.chkBlocker.TabIndex = 57;
      this.chkBlocker.Text = "Blocker";
      this.chkBlocker.Click += new System.EventHandler(this.Blocker_CheckedChanged);
      // 
      // label33
      // 
      this.label33.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label33.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label33.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label33.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label33.Location = new System.Drawing.Point(368, 280);
      this.label33.Name = "label33";
      this.label33.Size = new System.Drawing.Size(88, 16);
      this.label33.TabIndex = 121;
      this.label33.Text = "Blocker Dir";
      this.label33.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label34
      // 
      this.label34.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label34.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label34.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label34.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label34.Location = new System.Drawing.Point(368, 304);
      this.label34.Name = "label34";
      this.label34.Size = new System.Drawing.Size(88, 16);
      this.label34.TabIndex = 122;
      this.label34.Text = "Blocker Type";
      this.label34.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // txtBDir
      // 
      this.txtBDir.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtBDir.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtBDir.Location = new System.Drawing.Point(464, 280);
      this.txtBDir.Name = "txtBDir";
      this.txtBDir.Size = new System.Drawing.Size(192, 20);
      this.txtBDir.TabIndex = 123;
      this.txtBDir.Text = "";
      // 
      // txtBType
      // 
      this.txtBType.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtBType.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtBType.Location = new System.Drawing.Point(464, 304);
      this.txtBType.Name = "txtBType";
      this.txtBType.Size = new System.Drawing.Size(192, 20);
      this.txtBType.TabIndex = 124;
      this.txtBType.Text = "";
      // 
      // label35
      // 
      this.label35.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label35.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label35.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label35.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label35.Location = new System.Drawing.Point(368, 232);
      this.label35.Name = "label35";
      this.label35.Size = new System.Drawing.Size(88, 16);
      this.label35.TabIndex = 125;
      this.label35.Text = "Liquid Uses";
      this.label35.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // txtUses
      // 
      this.txtUses.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtUses.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtUses.Location = new System.Drawing.Point(464, 232);
      this.txtUses.Name = "txtUses";
      this.txtUses.Size = new System.Drawing.Size(192, 20);
      this.txtUses.TabIndex = 126;
      this.txtUses.Text = "";
      // 
      // label36
      // 
      this.label36.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label36.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label36.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label36.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label36.Location = new System.Drawing.Point(368, 208);
      this.label36.Name = "label36";
      this.label36.Size = new System.Drawing.Size(88, 16);
      this.label36.TabIndex = 127;
      this.label36.Text = "Liquid";
      this.label36.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // txtLiquid
      // 
      this.txtLiquid.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtLiquid.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtLiquid.Location = new System.Drawing.Point(464, 208);
      this.txtLiquid.Name = "txtLiquid";
      this.txtLiquid.Size = new System.Drawing.Size(192, 20);
      this.txtLiquid.TabIndex = 128;
      this.txtLiquid.Text = "";
      // 
      // cmdSelectAll
      // 
      this.cmdSelectAll.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdSelectAll.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdSelectAll.Font = new System.Drawing.Font("Arial", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.cmdSelectAll.Location = new System.Drawing.Point(208, 480);
      this.cmdSelectAll.Name = "cmdSelectAll";
      this.cmdSelectAll.Size = new System.Drawing.Size(72, 24);
      this.cmdSelectAll.TabIndex = 52;
      this.cmdSelectAll.Text = "Select All";
      this.cmdSelectAll.Click += new System.EventHandler(this.cmdSelectAll_Click);
      // 
      // chkEqLight
      // 
      this.chkEqLight.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkEqLight.BackgroundImage")));
      this.chkEqLight.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkEqLight.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkEqLight.Location = new System.Drawing.Point(96, 568);
      this.chkEqLight.Name = "chkEqLight";
      this.chkEqLight.Size = new System.Drawing.Size(168, 16);
      this.chkEqLight.TabIndex = 61;
      this.chkEqLight.Text = "Equipable Lightsource";
      this.chkEqLight.Click += new System.EventHandler(this.chkLightsource_CheckedChanged);
      // 
      // label37
      // 
      this.label37.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label37.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label37.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label37.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label37.Location = new System.Drawing.Point(368, 184);
      this.label37.Name = "label37";
      this.label37.Size = new System.Drawing.Size(88, 16);
      this.label37.TabIndex = 131;
      this.label37.Text = "Light Ticks";
      this.label37.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // txtLightTicks
      // 
      this.txtLightTicks.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtLightTicks.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtLightTicks.Location = new System.Drawing.Point(464, 184);
      this.txtLightTicks.Name = "txtLightTicks";
      this.txtLightTicks.Size = new System.Drawing.Size(72, 20);
      this.txtLightTicks.TabIndex = 132;
      this.txtLightTicks.Text = "";
      // 
      // chkInvisible
      // 
      this.chkInvisible.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkInvisible.BackgroundImage")));
      this.chkInvisible.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkInvisible.Location = new System.Drawing.Point(416, 392);
      this.chkInvisible.Name = "chkInvisible";
      this.chkInvisible.Size = new System.Drawing.Size(88, 16);
      this.chkInvisible.TabIndex = 134;
      this.chkInvisible.Text = "INVISIBLE";
      // 
      // chkUntouchable
      // 
      this.chkUntouchable.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkUntouchable.BackgroundImage")));
      this.chkUntouchable.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkUntouchable.Location = new System.Drawing.Point(416, 408);
      this.chkUntouchable.Name = "chkUntouchable";
      this.chkUntouchable.Size = new System.Drawing.Size(96, 16);
      this.chkUntouchable.TabIndex = 135;
      this.chkUntouchable.Text = "UNTOUCHABLE";
      // 
      // chkGood
      // 
      this.chkGood.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkGood.BackgroundImage")));
      this.chkGood.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkGood.Location = new System.Drawing.Point(328, 392);
      this.chkGood.Name = "chkGood";
      this.chkGood.Size = new System.Drawing.Size(80, 16);
      this.chkGood.TabIndex = 136;
      this.chkGood.Text = "GOOD";
      // 
      // chkEvil
      // 
      this.chkEvil.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkEvil.BackgroundImage")));
      this.chkEvil.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkEvil.Location = new System.Drawing.Point(328, 408);
      this.chkEvil.Name = "chkEvil";
      this.chkEvil.Size = new System.Drawing.Size(80, 16);
      this.chkEvil.TabIndex = 137;
      this.chkEvil.Text = "EVIL";
      // 
      // chkNeutral
      // 
      this.chkNeutral.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNeutral.BackgroundImage")));
      this.chkNeutral.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNeutral.Location = new System.Drawing.Point(328, 424);
      this.chkNeutral.Name = "chkNeutral";
      this.chkNeutral.Size = new System.Drawing.Size(80, 16);
      this.chkNeutral.TabIndex = 138;
      this.chkNeutral.Text = "NEUTRAL";
      // 
      // chkBurning
      // 
      this.chkBurning.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkBurning.BackgroundImage")));
      this.chkBurning.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkBurning.Location = new System.Drawing.Point(328, 440);
      this.chkBurning.Name = "chkBurning";
      this.chkBurning.Size = new System.Drawing.Size(80, 16);
      this.chkBurning.TabIndex = 139;
      this.chkBurning.Text = "BURNING";
      // 
      // chkFreezing
      // 
      this.chkFreezing.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkFreezing.BackgroundImage")));
      this.chkFreezing.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkFreezing.Location = new System.Drawing.Point(328, 456);
      this.chkFreezing.Name = "chkFreezing";
      this.chkFreezing.Size = new System.Drawing.Size(80, 16);
      this.chkFreezing.TabIndex = 140;
      this.chkFreezing.Text = "FREEZING";
      // 
      // chkShocking
      // 
      this.chkShocking.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkShocking.BackgroundImage")));
      this.chkShocking.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkShocking.Location = new System.Drawing.Point(328, 472);
      this.chkShocking.Name = "chkShocking";
      this.chkShocking.Size = new System.Drawing.Size(80, 16);
      this.chkShocking.TabIndex = 141;
      this.chkShocking.Text = "SHOCKING";
      // 
      // chkNoRent
      // 
      this.chkNoRent.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNoRent.BackgroundImage")));
      this.chkNoRent.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNoRent.Location = new System.Drawing.Point(416, 472);
      this.chkNoRent.Name = "chkNoRent";
      this.chkNoRent.Size = new System.Drawing.Size(104, 16);
      this.chkNoRent.TabIndex = 142;
      this.chkNoRent.Text = "NO RENT";
      // 
      // cmdExact
      // 
      this.cmdExact.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdExact.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdExact.Font = new System.Drawing.Font("Courier New", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.cmdExact.Location = new System.Drawing.Point(24, 144);
      this.cmdExact.Name = "cmdExact";
      this.cmdExact.Size = new System.Drawing.Size(24, 88);
      this.cmdExact.TabIndex = 143;
      this.cmdExact.Text = "Exact";
      this.cmdExact.Click += new System.EventHandler(this.cmdExact_Click);
      // 
      // chkExact
      // 
      this.chkExact.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkExact.BackgroundImage")));
      this.chkExact.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkExact.Location = new System.Drawing.Point(30, 240);
      this.chkExact.Name = "chkExact";
      this.chkExact.Size = new System.Drawing.Size(16, 16);
      this.chkExact.TabIndex = 144;
      this.chkExact.CheckedChanged += new System.EventHandler(this.chkExact_CheckedChanged);
      // 
      // frmItemEditor
      // 
      this.AutoScaleBaseSize = new System.Drawing.Size(7, 13);
      this.BackColor = System.Drawing.SystemColors.Menu;
      this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
      this.ClientSize = new System.Drawing.Size(662, 676);
      this.Controls.Add(this.chkExact);
      this.Controls.Add(this.cmdExact);
      this.Controls.Add(this.chkNoRent);
      this.Controls.Add(this.chkShocking);
      this.Controls.Add(this.chkFreezing);
      this.Controls.Add(this.chkBurning);
      this.Controls.Add(this.chkNeutral);
      this.Controls.Add(this.chkEvil);
      this.Controls.Add(this.chkGood);
      this.Controls.Add(this.chkUntouchable);
      this.Controls.Add(this.chkInvisible);
      this.Controls.Add(this.txtLightTicks);
      this.Controls.Add(this.txtLiquid);
      this.Controls.Add(this.txtUses);
      this.Controls.Add(this.txtBType);
      this.Controls.Add(this.txtBDir);
      this.Controls.Add(this.txtCharges);
      this.Controls.Add(this.txtLName);
      this.Controls.Add(this.txtMaxItems);
      this.Controls.Add(this.txtBoardName);
      this.Controls.Add(this.txtRes);
      this.Controls.Add(this.txtAC);
      this.Controls.Add(this.txtDam);
      this.Controls.Add(this.txtHit);
      this.Controls.Add(this.txtWorth);
      this.Controls.Add(this.txtReqWis);
      this.Controls.Add(this.txtReqInt);
      this.Controls.Add(this.txtReqCon);
      this.Controls.Add(this.txtReqDex);
      this.Controls.Add(this.txtReqStr);
      this.Controls.Add(this.txtMV);
      this.Controls.Add(this.txtMN);
      this.Controls.Add(this.txtHP);
      this.Controls.Add(this.txtLevel);
      this.Controls.Add(this.txtGroundDesc);
      this.Controls.Add(this.txtLookAtDesc);
      this.Controls.Add(this.txtName);
      this.Controls.Add(this.txtID);
      this.Controls.Add(this.label37);
      this.Controls.Add(this.cmdSelectAll);
      this.Controls.Add(this.label36);
      this.Controls.Add(this.label35);
      this.Controls.Add(this.label34);
      this.Controls.Add(this.label33);
      this.Controls.Add(this.chkBlocker);
      this.Controls.Add(this.chkNoDrop);
      this.Controls.Add(this.chkNoSacrifice);
      this.Controls.Add(this.chkNoGet);
      this.Controls.Add(this.chkHumming);
      this.Controls.Add(this.chkGlowing);
      this.Controls.Add(this.chkIngredient);
      this.Controls.Add(this.chkPerish);
      this.Controls.Add(this.chkBlast);
      this.Controls.Add(this.chkGouge);
      this.Controls.Add(this.chkThrust);
      this.Controls.Add(this.chkFist);
      this.Controls.Add(this.chkChop);
      this.Controls.Add(this.chkWhip);
      this.Controls.Add(this.chkStar);
      this.Controls.Add(this.chkArrow);
      this.Controls.Add(this.chkSlash);
      this.Controls.Add(this.chkPierce);
      this.Controls.Add(this.chkBludgeon);
      this.Controls.Add(this.chkStab);
      this.Controls.Add(this.label15);
      this.Controls.Add(this.label14);
      this.Controls.Add(this.cmdRemoveCharged);
      this.Controls.Add(this.cmdAddCharged);
      this.Controls.Add(this.cboCharged);
      this.Controls.Add(this.label13);
      this.Controls.Add(this.cmdRemInnate);
      this.Controls.Add(this.cmdAddInnate);
      this.Controls.Add(this.cboInnate);
      this.Controls.Add(this.label12);
      this.Controls.Add(this.chkFountain);
      this.Controls.Add(this.chkScroll);
      this.Controls.Add(this.chkRefill);
      this.Controls.Add(this.chkDrink);
      this.Controls.Add(this.chkFood);
      this.Controls.Add(this.chkBoard);
      this.Controls.Add(this.chkContainer);
      this.Controls.Add(this.chkNormal);
      this.Controls.Add(this.chkEqContainer);
      this.Controls.Add(this.chkEquipment);
      this.Controls.Add(this.label11);
      this.Controls.Add(this.label32);
      this.Controls.Add(this.label31);
      this.Controls.Add(this.label10);
      this.Controls.Add(this.label9);
      this.Controls.Add(this.label30);
      this.Controls.Add(this.cmdRemoveID);
      this.Controls.Add(this.cmdAddID);
      this.Controls.Add(this.cboLoadIDs);
      this.Controls.Add(this.cmdRemoveSpell);
      this.Controls.Add(this.cmdAddSpell);
      this.Controls.Add(this.cboPotionSpells);
      this.Controls.Add(this.label29);
      this.Controls.Add(this.label28);
      this.Controls.Add(this.label27);
      this.Controls.Add(this.cmdCancel);
      this.Controls.Add(this.cmdSave);
      this.Controls.Add(this.label26);
      this.Controls.Add(this.label25);
      this.Controls.Add(this.label24);
      this.Controls.Add(this.label18);
      this.Controls.Add(this.label17);
      this.Controls.Add(this.label16);
      this.Controls.Add(this.lblWorth);
      this.Controls.Add(this.label19);
      this.Controls.Add(this.label20);
      this.Controls.Add(this.label21);
      this.Controls.Add(this.label22);
      this.Controls.Add(this.label23);
      this.Controls.Add(this.label8);
      this.Controls.Add(this.label7);
      this.Controls.Add(this.label6);
      this.Controls.Add(this.label5);
      this.Controls.Add(this.label4);
      this.Controls.Add(this.label3);
      this.Controls.Add(this.label2);
      this.Controls.Add(this.label1);
      this.Controls.Add(this.chkPa);
      this.Controls.Add(this.chkBr);
      this.Controls.Add(this.chkKn);
      this.Controls.Add(this.chkWa);
      this.Controls.Add(this.chkAs);
      this.Controls.Add(this.chkSh);
      this.Controls.Add(this.chkSm);
      this.Controls.Add(this.chkSc);
      this.Controls.Add(this.chkWl);
      this.Controls.Add(this.chkIl);
      this.Controls.Add(this.chkWi);
      this.Controls.Add(this.chkMg);
      this.Controls.Add(this.chkAl);
      this.Controls.Add(this.chkHl);
      this.Controls.Add(this.chkDc);
      this.Controls.Add(this.chkPr);
      this.Controls.Add(this.chkMo);
      this.Controls.Add(this.chkDr);
      this.Controls.Add(this.chkCl);
      this.Controls.Add(this.chkMc);
      this.Controls.Add(this.chkMe);
      this.Controls.Add(this.chkSb);
      this.Controls.Add(this.chkRa);
      this.Controls.Add(this.chkRg);
      this.Controls.Add(this.chkTh);
      this.Controls.Add(this.chkAp);
      this.Controls.Add(this.chkCr);
      this.Controls.Add(this.chkDk);
      this.Controls.Add(this.chkREar);
      this.Controls.Add(this.chkLEar);
      this.Controls.Add(this.chkLegs);
      this.Controls.Add(this.chkWaist);
      this.Controls.Add(this.chkHands);
      this.Controls.Add(this.chkBack);
      this.Controls.Add(this.chkFeet);
      this.Controls.Add(this.chkHead);
      this.Controls.Add(this.chkBody);
      this.Controls.Add(this.chkRHand);
      this.Controls.Add(this.chkLHand);
      this.Controls.Add(this.chkShield);
      this.Controls.Add(this.chkWeapon);
      this.Controls.Add(this.chkNeck);
      this.Controls.Add(this.chkRFinger);
      this.Controls.Add(this.chkLFinger);
      this.Controls.Add(this.chkRWrist);
      this.Controls.Add(this.chkLWrist);
      this.Controls.Add(this.chkEqLight);
      this.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
      this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
      this.MaximizeBox = false;
      this.MinimizeBox = false;
      this.Name = "frmItemEditor";
      this.ShowInTaskbar = false;
      this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
      this.Text = "Item Editor";
      this.ResumeLayout(false);

    }
    #endregion

    private void toggle_types(CheckBox chkTemp, bool value)

    {
      chkEquipment.Enabled = value;
      chkEqContainer.Enabled = value;
      chkNormal.Enabled = value;
      chkContainer.Enabled = value;
      chkBoard.Enabled = value;
      chkFood.Enabled = value;
      chkDrink.Enabled = value;
      chkRefill.Enabled = value;
      chkScroll.Enabled = value;
      chkFountain.Enabled = value;
      chkBlocker.Enabled = value;
      chkEqLight.Enabled = value;

      if (value == false)

      {
        chkTemp.Enabled = true;
        chkTemp.Focus();
      }
    }

    private void Change_Click(object sender, System.EventArgs e)

    {
      if (watch_change)
        changed = true;
    }

    private void cmdSave_Click(object sender, System.EventArgs e)

    {
      if (changed)

      {
        if (!validate()) return;

        if (original_id == txtID.Text)
          if (item_list.find_item(txtID.Text) == null)

          {
            saved  = true;
            active = false;
            save_item();
            item_list.add_item(active_item);
            this.Hide();
          }

          else

          {
            item_list.remove_item(item_list.find_item(txtID.Text));
            save_item();
            item_list.add_item(active_item);
            active = false;
            this.Hide();
          }

        else

        {
          if (item_list.find_item(txtID.Text) == null)

          {
            if (original_id != "")
              if (item_list.find_item(original_id) != null)
                item_list.remove_item(item_list.find_item(original_id));

            save_item();
            item_list.add_item(active_item);
            active = false;
            this.Hide();
          }

          else
            if (MessageBox.Show ("Item ID changed. This will overwrite the existing item with the id of " + txtID.Text, "Item Replacement Warning",
            MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)

          {
            if (item_list.find_item(original_id) != null)
              item_list.remove_item(item_list.find_item(original_id));

            if (item_list.find_item(txtID.Text) != null)
              item_list.remove_item(item_list.find_item(txtID.Text));

            save_item();
            item_list.add_item(active_item);
            active = false;
            this.Hide();
          }
        }
      }

      else

      {
        active = false;
        saved = false;
        this.Hide();
      }
    }

    private void cmdCancel_Click(object sender, System.EventArgs e)

    {
      if (changed)

      {
        if (MessageBox.Show ("Do you want to save your changes?", "Item Cancelled",
          MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.No)

        {
          saved  = false;
          active = false;
          this.Hide();
        }

        else
          cmdSave_Click(sender, e);
      }

      else

      {
        saved = false;
        active = false;
        this.Hide();
      }
    }

    private void cmdAddSpell_Click(object sender, System.EventArgs e)

    {
      string temp = cboPotionSpells.Text.ToLower();

      if (temp != "")
        if (cboPotionSpells.Items.IndexOf(temp) == -1) {
          cboPotionSpells.Items.Add(temp);
          cboPotionSpells.Text = ""; }
    }

    private void cmdRemoveSpell_Click(object sender, System.EventArgs e)

    {
      string temp = cboPotionSpells.Text.ToLower();

      if (cboPotionSpells.Items.IndexOf(temp) != -1)
        cboPotionSpells.Items.Remove(temp);
    }

    private void cmdAddID_Click(object sender, System.EventArgs e)

    {
      string temp = cboLoadIDs.Text.ToLower();

      if (temp == "") {
        MessageBox.Show("Invalid item ID.");
        return; }
        
      try { Int32.Parse(cboLoadIDs.Text); }
      catch(System.FormatException) {
        MessageBox.Show("Invalid item ID.");
        return; }
      
      if (cboLoadIDs.Items.IndexOf(temp) == -1) {
        cboLoadIDs.Items.Add(temp);
        cboLoadIDs.Text = ""; }
    }

    private void cmdRemoveID_Click(object sender, System.EventArgs e)

    {
      string temp = cboLoadIDs.Text.ToLower();

      if (cboLoadIDs.Items.IndexOf(temp) != -1)
        cboLoadIDs.Items.Remove(temp);

      cboLoadIDs.Text = "";
    }

    private void chkContainer_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
      if ((chkContainer.Checked) || (chkEqContainer.Checked))

      {
        txtMaxItems.Text = "0";
        txtMaxItems.Enabled = true;
        cboLoadIDs.Enabled = true;
      }

      else

      {
        cboLoadIDs.Text = "";
        txtMaxItems.Enabled = false;
        cboLoadIDs.Enabled = false;
        txtMaxItems.Text = "";
      }
    }

    private void chkBoard_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
        if (chkBoard.Checked)

        {
          txtBoardName.Enabled = true;
          txtBoardName.Text = "";
        }

        else

        {
          txtBoardName.Enabled = false;
          txtBoardName.Text = "";
        }
    }

    private void cmdAddInnate_Click(object sender, System.EventArgs e)

    {
      if (chkEquipment.Checked || chkEqContainer.Checked)

      {
        string temp = cboInnate.Text.ToLower();

        if (temp != "")
          if (cboInnate.Items.IndexOf(temp) == -1) {
            cboInnate.Items.Add(temp);
            cboInnate.Text = ""; }
      }
    }
    private void chkEquipment_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)

      {
        if ((chkEquipment.Checked) || (chkEqContainer.Checked) || (chkEqLight.Checked))

        {
          cboInnate.Enabled = true;
          cboCharged.Enabled = true;
          txtCharges.Enabled = true;
          txtCharges.Text = "";
          toggle_classes(true);
          toggle_places(true);
          toggle_requirements(true);
        }

        else

        {
          cboInnate.Enabled = false;
          cboCharged.Enabled = false;
          txtCharges.Enabled = false;
          txtCharges.Text = "";
          toggle_dtype(false);
          toggle_classes(false);
          toggle_places(false);
          toggle_requirements(false);
        }
      }
    }

    private void chkEqContainer_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)

      {
        chkEquipment_CheckedChanged(sender, e);
        chkContainer_CheckedChanged(sender, e);
      }
    }
    private void Consumable_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
        if (chkScroll.Checked)

        {
          cboPotionSpells.Enabled = true;
          cboPotionSpells.Items.Clear();
        }

        else

        {
          cboPotionSpells.Enabled = false;
          cboPotionSpells.Items.Clear();
        }
    }
    private void Blocker_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
        if (chkBlocker.Checked)

        {
          txtBType.Enabled = true;
          txtBType.Text = "[Enter Blocker Type Here]";
          txtBDir.Enabled = true;
          txtBDir.Text = "[Enter Direction Here]";
        }

        else

        {
          txtBType.Enabled = false;
          txtBType.Text = "";
          txtBDir.Enabled = false;
          txtBDir.Text = "";
        }
    }

    private void Refill_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
        if ((chkRefill.Checked) || (chkDrink.Checked))

        {
          txtUses.Enabled = true;
          txtUses.Text = "";
          txtLiquid.Enabled = true;
          txtLiquid.Text = "";
          
          if (chkDrink.Checked)
          
          {
            txtUses.Enabled = true;
            txtUses.Text = "1";
            txtUses.ReadOnly = true;
          }          
        }

        else

        {
          txtUses.Enabled = false;
          txtUses.Text = "";
          txtLiquid.Enabled = false;
          txtLiquid.Text = "";
          txtUses.ReadOnly = false;
        }
    }

    private void cmdRemInnate_Click(object sender, System.EventArgs e)

    {
      string temp = cboInnate.Text.ToLower();

      if (cboInnate.Items.IndexOf(temp) != -1)
        cboInnate.Items.Remove(temp);

      cboInnate.Text = "";
    }

    private void cmdAddCharged_Click(object sender, System.EventArgs e)

    {
      string temp = cboCharged.Text.ToLower();

      if (temp != "")
        if (cboCharged.Items.IndexOf(temp) == -1) {
          cboCharged.Items.Add(temp);
          cboCharged.Text = ""; }
    }

    private void cmdRemoveCharged_Click(object sender, System.EventArgs e)

    {
      string temp = cboCharged.Text.ToLower();

      if (cboCharged.Items.IndexOf(temp) != -1)
        cboCharged.Items.Remove(temp);

      cboCharged.Text = "";
    }

    private void TypeChecked(object sender, System.EventArgs e)

    {
      CheckBox temp = (CheckBox)sender;
      toggle_types(temp, !temp.Checked);
    }

    private void chkLightsource_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
        if (chkEqLight.Checked)

        {

          txtLightTicks.Enabled = true;
          txtLightTicks.Text = "0";
          chkEquipment_CheckedChanged(sender, e);
        }

        else

        {
          txtLightTicks.Enabled = false;
          txtLightTicks.Text = "";
          chkEquipment_CheckedChanged(sender, e);
        }
    }

    private void chkFountain_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
        if (chkFountain.Checked)

        {
          txtLiquid.Enabled = true;
          txtLiquid.Text = "Water";
        }

        else

        {
          txtLiquid.Enabled = false;
          txtLiquid.Text = "";
        }
    }

    protected override void OnPaint(PaintEventArgs e)

    {
      watch_change = false;

      base.OnPaint (e);
      cmdSave.Focus();

      watch_change = true;
    }
    protected override void OnClosing(CancelEventArgs e)
    {
      base.OnClosing (e);

      if (active)
        cmdCancel_Click(null, e);

      if (active)
        e.Cancel = true;
    }

    protected override void Dispose(bool disposing)
    {
      if( disposing )
      {
        if(components != null)
        {
          components.Dispose();
        }
      }
      base.Dispose( disposing );
    }

    private void cmdSelectAll_Click(object sender, System.EventArgs e)

    {
      if (!chkEquipment.Checked && !chkEqContainer.Checked && !chkEqLight.Checked)
        return;

      chkWa.Checked = true;
      chkKn.Checked = true;
      chkBr.Checked = true;
      chkPa.Checked = true;
      chkDk.Checked = true;
      chkCr.Checked = true;
      chkAp.Checked = true;
      chkTh.Checked = true;
      chkAs.Checked = true;
      chkRg.Checked = true;
      chkRa.Checked = true;
      chkSb.Checked = true;
      chkMe.Checked = true;
      chkMc.Checked = true;
      chkCl.Checked = true;
      chkDr.Checked = true;
      chkMo.Checked = true;
      chkPr.Checked = true;
      chkDc.Checked = true;
      chkHl.Checked = true;
      chkAl.Checked = true;
      chkMg.Checked = true;
      chkWi.Checked = true;
      chkIl.Checked = true;
      chkWl.Checked = true;
      chkSc.Checked = true;
      chkSm.Checked = true;
      chkSh.Checked = true;
    }

    private void toggle_dtype(bool val)

    {
      chkStab.Checked = false;
      chkSlash.Checked = false;
      chkPierce.Checked = false;
      chkBludgeon.Checked = false;
      chkChop.Checked = false;
      chkWhip.Checked = false;
      chkArrow.Checked = false;
      chkStar.Checked = false;
      chkFist.Checked = false;
      chkThrust.Checked = false;
      chkGouge.Checked = false;
      chkBlast.Checked = false;

      chkStab.Enabled = val;
      chkSlash.Enabled = val;
      chkPierce.Enabled = val;
      chkBludgeon.Enabled = val;
      chkChop.Enabled = val;
      chkWhip.Enabled = val;
      chkArrow.Enabled = val;
      chkStar.Enabled = val;
      chkFist.Enabled = val;
      chkThrust.Enabled = val;
      chkGouge.Enabled = val;
      chkBlast.Enabled = val;
    }

    private void toggle_classes(bool val)

    {
      chkWa.Checked = false;
      chkKn.Checked = false;
      chkBr.Checked = false;
      chkPa.Checked = false;
      chkDk.Checked = false;
      chkCr.Checked = false;
      chkAp.Checked = false;
      chkTh.Checked = false;
      chkAs.Checked = false;
      chkRg.Checked = false;
      chkRa.Checked = false;
      chkSb.Checked = false;
      chkMe.Checked = false;
      chkMc.Checked = false;
      chkCl.Checked = false;
      chkDr.Checked = false;
      chkMo.Checked = false;
      chkPr.Checked = false;
      chkDc.Checked = false;
      chkHl.Checked = false;
      chkAl.Checked = false;
      chkMg.Checked = false;
      chkWi.Checked = false;
      chkIl.Checked = false;
      chkWl.Checked = false;
      chkSc.Checked = false;
      chkSm.Checked = false;
      chkSh.Checked = false;

      chkWa.Enabled = val;
      chkKn.Enabled = val;
      chkBr.Enabled = val;
      chkPa.Enabled = val;
      chkDk.Enabled = val;
      chkCr.Enabled = val;
      chkAp.Enabled = val;
      chkTh.Enabled = val;
      chkAs.Enabled = val;
      chkRg.Enabled = val;
      chkRa.Enabled = val;
      chkSb.Enabled = val;
      chkMe.Enabled = val;
      chkMc.Enabled = val;
      chkCl.Enabled = val;
      chkDr.Enabled = val;
      chkMo.Enabled = val;
      chkPr.Enabled = val;
      chkDc.Enabled = val;
      chkHl.Enabled = val;
      chkAl.Enabled = val;
      chkMg.Enabled = val;
      chkWi.Enabled = val;
      chkIl.Enabled = val;
      chkWl.Enabled = val;
      chkSc.Enabled = val;
      chkSm.Enabled = val;
      chkSh.Enabled = val;
    }
    private void toggle_places(bool val)

    {
      chkWeapon.Checked = false;
      chkShield.Checked = false;
      chkLHand.Checked = false;
      chkRHand.Checked = false;
      chkBody.Checked = false;
      chkHead.Checked = false;
      chkFeet.Checked = false;
      chkBack.Checked = false;
      chkHands.Checked = false;
      chkWaist.Checked = false;
      chkLegs.Checked = false;
      chkLEar.Checked = false;
      chkREar.Checked = false;
      chkLWrist.Checked = false;
      chkRWrist.Checked = false;
      chkLFinger.Checked = false;
      chkRFinger.Checked = false;
      chkNeck.Checked = false;

      chkWeapon.Enabled = val;
      chkShield.Enabled = val;
      chkLHand.Enabled = val;
      chkRHand.Enabled = val;
      chkBody.Enabled = val;
      chkHead.Enabled = val;
      chkFeet.Enabled = val;
      chkBack.Enabled = val;
      chkHands.Enabled = val;
      chkWaist.Enabled = val;
      chkLegs.Enabled = val;
      chkLEar.Enabled = val;
      chkREar.Enabled = val;
      chkLWrist.Enabled = val;
      chkRWrist.Enabled = val;
      chkLFinger.Enabled = val;
      chkRFinger.Enabled = val;
      chkNeck.Enabled = val;
    }

    private void toggle_requirements(bool val)

    {
      txtMN.Enabled = val;
      txtHP.Enabled = val;
      txtMV.Enabled = val;
      txtHit.Enabled = val;
      txtDam.Enabled = val;
      txtRes.Enabled = val;
      txtAC.Enabled = val;
    }

    private void cmdExact_Click(object sender, System.EventArgs e)
    
    {
      if (!chkExact.Checked) return;
      
      frmExact ExactLook = new frmExact();
      ExactLook.set_exact(exact_holder);
      ExactLook.ShowDialog();
      
      if (ExactLook.get_saved())
        exact_holder = ExactLook.get_exact();
    }

    private void chkExact_CheckedChanged(object sender, System.EventArgs e)
    
    {
      if (!active) return;
      
      if (chkExact.Checked)
      
      {
        if (MessageBox.Show("Going to exact mode will erase your current description.  Continue?",
                            "Exact Mode", MessageBoxButtons.OKCancel) == DialogResult.Cancel)
          return;
        
        txtLookAtDesc.ReadOnly = true;
        txtLookAtDesc.BackColor = Color.FromName("Control");
        txtLookAtDesc.Text = "EXACT DESCRIPTION";
      }
      
      else
      
      {
        txtLookAtDesc.ReadOnly = false;
        txtLookAtDesc.BackColor = Color.FromName("Window");
        txtLookAtDesc.Text = "";
        exact_holder = "";
      }
    }
    private void DTypeChecked(object sender, System.EventArgs e)
    
    {      
      if (control_dtype) return;
      
      control_dtype = true;
      
      CheckBox temp = (CheckBox)sender;
      
      if (temp.Checked) 
      
      {
        toggle_dtype(false);
        temp.Enabled = true;
        temp.Checked = true;
      }
      
      else toggle_dtype(true);        
      
      control_dtype = false;
    }
    
    private void WeaponChecked(object sender, System.EventArgs e)
    
    {
      CheckBox temp = (CheckBox)sender;
      
      control_dtype = true;
      
      if (temp.Checked)
        toggle_dtype(true);
      
      else toggle_dtype(false);        
      
      control_dtype = false;
    }
  }
}
