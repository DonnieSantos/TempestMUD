using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace Tempest_Builder

{
  public class frmMobEditor : System.Windows.Forms.Form

  {
    private frmItemPicker item_picker;
    private mob active_mob;
    private bool active, saved, changed;
    private moblist mob_list;
    private ArrayList load_items_freq,
                      skills, spells,
                      wear_items_freq,
                      actions;
    private itemlist litems, sitems, witems;
    private string original_id;
    private bool watch_change;

    #region GUI Variables
    private System.Windows.Forms.Button cmdSave;
    private System.Windows.Forms.Button cmdCancel;
    private System.Windows.Forms.Label label1;
    private System.Windows.Forms.TextBox txtID;
    private System.Windows.Forms.Label label2;
    private System.Windows.Forms.TextBox txtName;
    private System.Windows.Forms.Label label3;
    private System.Windows.Forms.TextBox txtKeywords;
    private System.Windows.Forms.Label label4;
    private System.Windows.Forms.TextBox txtRoomDesc;
    private System.Windows.Forms.Label label5;
    private System.Windows.Forms.TextBox txtLookDesc;
    private System.Windows.Forms.Label label6;
    private System.Windows.Forms.Label label7;
    private System.Windows.Forms.Label label8;
    private System.Windows.Forms.Label label9;
    private System.Windows.Forms.Label label10;
    private System.Windows.Forms.Label label11;
    private System.Windows.Forms.Label label12;
    private System.Windows.Forms.TextBox txtLevel;
    private System.Windows.Forms.TextBox txtHitPoints;
    private System.Windows.Forms.TextBox txtMana;
    private System.Windows.Forms.TextBox txtExp;
    private System.Windows.Forms.TextBox txtGold;
    private System.Windows.Forms.TextBox txtAllign;
    private System.Windows.Forms.TextBox txtSpeed;
    private System.Windows.Forms.CheckBox chkNPC;
    private System.Windows.Forms.CheckBox chkMerchant;
    private System.Windows.Forms.CheckBox chkBanker;
    private System.Windows.Forms.CheckBox chkBard;
    private System.Windows.Forms.CheckBox chkMobile;
    private System.Windows.Forms.CheckBox chkGuard;
    private System.Windows.Forms.CheckBox chkSkillmaster;
    private System.Windows.Forms.CheckBox chkSpellmaster;
    private System.Windows.Forms.Label label13;
    private System.Windows.Forms.Label label14;
    private System.Windows.Forms.Label label15;
    private System.Windows.Forms.Label label16;
    private System.Windows.Forms.Label label17;
    private System.Windows.Forms.Label label18;
    private System.Windows.Forms.Label label19;
    private System.Windows.Forms.Label label20;
    private System.Windows.Forms.Label label21;
    private System.Windows.Forms.Label label22;
    private System.Windows.Forms.RadioButton rdoMale;
    private System.Windows.Forms.RadioButton rdoFemale;
    private System.Windows.Forms.ListBox lstActions;
    private System.Windows.Forms.Button cmdAddAction;
    private System.Windows.Forms.Label label23;
    private System.Windows.Forms.Label label24;
    private System.Windows.Forms.Label label25;
    private System.Windows.Forms.Label label26;
    private System.Windows.Forms.Label label27;
    private System.Windows.Forms.Button cmdAddLoadItem;
    private System.Windows.Forms.Button cmdRemoveLoadItem;
    private System.Windows.Forms.Button cmdAddWearItem;
    private System.Windows.Forms.Button cmdRemoveWearItem;
    private System.Windows.Forms.Button cmdAddSkill;
    private System.Windows.Forms.Button cmdRemoveSkill;
    private System.Windows.Forms.Button cmdAddSellItem;
    private System.Windows.Forms.Button cmdRemoveSellItem;
    private System.Windows.Forms.TextBox txtShopName;
    private System.Windows.Forms.TextBox txtSTR;
    private System.Windows.Forms.TextBox txtINT;
    private System.Windows.Forms.TextBox txtWIS;
    private System.Windows.Forms.TextBox txtDEX;
    private System.Windows.Forms.TextBox txtCON;
    private System.Windows.Forms.TextBox txtHIT;
    private System.Windows.Forms.TextBox txtDAM;
    private System.Windows.Forms.TextBox txtAC;
    private System.Windows.Forms.TextBox txtMR;
    private System.Windows.Forms.ListBox lstLoadItems;
    private System.Windows.Forms.ListBox lstWearItems;
    private System.Windows.Forms.ListBox lstSellItems;
    private System.Windows.Forms.Label label28;
    private System.Windows.Forms.Label label29;
    private System.Windows.Forms.Label label30;
    private System.Windows.Forms.Label label31;
    private System.Windows.Forms.ListBox lstSkills;
    private System.Windows.Forms.TextBox txtSkillName;
    private System.Windows.Forms.TextBox txtSkillFreq;
    private System.Windows.Forms.Label label32;
    private System.Windows.Forms.Label label33;
    private System.Windows.Forms.Label label34;
    private System.Windows.Forms.Label label35;
    private System.Windows.Forms.TextBox txtSpellFreq;
    private System.Windows.Forms.TextBox txtSpellName;
    private System.Windows.Forms.ListBox lstSpells;
    private System.Windows.Forms.Button cmdRemoveSpell;
    private System.Windows.Forms.Button cmdAddSpell;
    private System.Windows.Forms.Label label36;
    private System.Windows.Forms.Label label37;
    private System.Windows.Forms.TextBox txtAction;
    private System.Windows.Forms.TextBox txtActionFreq;
    private System.Windows.Forms.Button cmdRemoveAction;
    private System.Windows.Forms.Label lblBType;
    private System.Windows.Forms.Label lblBDir;
    private System.Windows.Forms.CheckBox chkBlocker;
    private System.Windows.Forms.TextBox txtBDir;
    private System.Windows.Forms.TextBox txtBType;
    private System.Windows.Forms.CheckBox chkClergy;
    private System.Windows.Forms.CheckBox chkTeacher;
    private System.Windows.Forms.CheckBox chkUndead;
    private System.Windows.Forms.CheckBox chkHerbalist;
    private System.Windows.Forms.CheckBox chkSancd;
    private System.Windows.Forms.CheckBox chkFlying;
    private System.Windows.Forms.CheckBox chkAggro;

    private System.ComponentModel.Container components = null;
    #endregion

    public frmMobEditor(moblist mlist, frmItemPicker ipicker)

    {
      InitializeComponent();
      item_picker = ipicker;
      mob_list = mlist;

      for (int i=0; i<this.Controls.Count; i++)
        if ((Controls[i] != cmdCancel) && (Controls[i] != cmdSave))
          Controls[i].GotFocus += new EventHandler(this.Changed_Click);
    }

    public bool get_saved()

    {
      return saved;
    }

    public void new_mob()

    {
      int current;
      int last = 0;
      bool found = false;

      active_mob = new mob();

      if (mob_list.get_size() == 0) {
        active_mob.id = frmMain.get_zone_id() + "00";
        found = true; }

      else if (mob_list.get_mob(0).id != frmMain.get_zone_id() + "00") {
        found = true;
        active_mob.id = frmMain.get_zone_id() + "00"; }

      else for (int i=0; i<mob_list.get_size(); i++)

      {
        current = Int32.Parse(mob_list.get_mob(i).id);
        if (i == 0) last = current - 1;

        if (current != last + 1)

        {
          found = true;
          last++;
          active_mob.id = last.ToString();
          i = mob_list.get_size();
        }

        last = current;
      }

      if (!found)

      {
        string str_id = mob_list.get_mob(mob_list.get_size()-1).id;
        int int_id = Int32.Parse(str_id);
        int_id++;
        active_mob.id = int_id.ToString();
      }

      edit_mob(active_mob);
    }

    public void edit_mob(mob mob_pointer)

    {
      active_mob = mob_pointer;

      original_id = active_mob.id;
      load_items_freq = new ArrayList(active_mob.load_items_freq);
      wear_items_freq = new ArrayList(active_mob.wear_items_freq);
      actions         = new ArrayList(active_mob.actions);
      skills          = new ArrayList(active_mob.skills);
      spells          = new ArrayList(active_mob.spells);

      litems = (itemlist)active_mob.litems.Clone();
      sitems = (itemlist)active_mob.sitems.Clone();
      witems = (itemlist)active_mob.witems.Clone();

      update_litems();
      update_witems();
      update_sitems();
      update_skillslist();
      update_spellslist();
      update_actionslist();

      txtSpellName.Text = "";
      txtSpellFreq.Text = "";
      txtSkillName.Text = "";
      txtSkillFreq.Text = "";
      txtAction.Text = "";
      txtActionFreq.Text = "";

      txtID.Text = active_mob.id;
      txtName.Text = active_mob.name;
      txtKeywords.Text = active_mob.keywords;
      txtRoomDesc.Text = active_mob.rdesc;
      txtLookDesc.Text = active_mob.ldesc;
      txtLevel.Text = active_mob.level;
      txtHitPoints.Text = active_mob.hitpoints;
      txtMana.Text = active_mob.mana;
      txtExp.Text = active_mob.experience;
      txtGold.Text = active_mob.gold;
      txtAllign.Text = active_mob.allign;
      txtSpeed.Text = active_mob.speed;
      txtSTR.Text = active_mob.strength;
      txtINT.Text = active_mob.intelligence;
      txtWIS.Text = active_mob.wisdom;
      txtDEX.Text = active_mob.dexerity;
      txtCON.Text = active_mob.constitution;
      txtHIT.Text = active_mob.hitroll;
      txtDAM.Text = active_mob.damroll;
      txtAC.Text = active_mob.armor_class;
      txtMR.Text = active_mob.magic_resistance;

      if (active_mob.merchant)

      {
        txtShopName.Text = active_mob.shop_name;
        txtShopName.ReadOnly = false;
      }

      else

      {
        txtShopName.Text = "";
        txtShopName.ReadOnly = true;
      }

      if (active_mob.blocker)

      {
        txtBType.Text = active_mob.btype;
        txtBType.ReadOnly = false;
        txtBDir.Text = active_mob.bdir;
        txtBDir.ReadOnly = false;
      }

      else

      {
        txtBType.Text = "";
        txtBType.ReadOnly = true;
        txtBDir.Text = "";
        txtBDir.ReadOnly = true;
      }

      if (active_mob.gender == "Male")
        rdoMale.Checked = true;
      else
        rdoFemale.Checked = true;

      if (active_mob.NPC) chkNPC.Checked = true;                 else chkNPC.Checked = false;
      if (active_mob.merchant) chkMerchant.Checked = true;       else chkMerchant.Checked = false;
      if (active_mob.guard) chkGuard.Checked = true;             else chkGuard.Checked = false;
      if (active_mob.mobile) chkMobile.Checked = true;           else chkMobile.Checked = false;
      if (active_mob.spellmaster) chkSpellmaster.Checked = true; else chkSpellmaster.Checked = false;
      if (active_mob.skillmaster) chkSkillmaster.Checked = true; else chkSkillmaster.Checked = false;
      if (active_mob.banker) chkBanker.Checked = true;           else chkBanker.Checked = false;
      if (active_mob.bard) chkBard.Checked = true;               else chkBard.Checked = false;
      if (active_mob.blocker) chkBlocker.Checked = true;         else chkBlocker.Checked = false;
      if (active_mob.aggro) chkAggro.Checked = true;
      if (active_mob.undead) chkUndead.Checked = true;
      if (active_mob.sancd) chkSancd.Checked = true;
      if (active_mob.flying) chkFlying.Checked = true;
      if (active_mob.clergy) chkClergy.Checked = true;
      if (active_mob.teacher) chkTeacher.Checked = true;
      if (active_mob.herbalist) chkHerbalist.Checked = true;
      
      saved = false;
      active = true;
      changed = false;
    }

    private void save_mob()

    {
      active_mob.id = Utility.clear_whitespace(txtID.Text);
      active_mob.name = Utility.clear_whitespace(txtName.Text);
      active_mob.keywords = Utility.clear_whitespace(txtKeywords.Text);
      active_mob.rdesc = Utility.clear_whitespace(txtRoomDesc.Text);
      active_mob.ldesc = Utility.clear_whitespace(txtLookDesc.Text);
      active_mob.level = Utility.clear_whitespace(txtLevel.Text);
      active_mob.hitpoints = Utility.clear_whitespace(txtHitPoints.Text);
      active_mob.mana = Utility.clear_whitespace(txtMana.Text);
      active_mob.experience = Utility.clear_whitespace(txtExp.Text);
      active_mob.gold = Utility.clear_whitespace(txtGold.Text);
      active_mob.allign = Utility.clear_whitespace(txtAllign.Text);
      active_mob.speed = Utility.clear_whitespace(txtSpeed.Text);
      active_mob.strength = Utility.clear_whitespace(txtSTR.Text);
      active_mob.intelligence = Utility.clear_whitespace(txtINT.Text);
      active_mob.wisdom = Utility.clear_whitespace(txtWIS.Text);
      active_mob.dexerity = Utility.clear_whitespace(txtDEX.Text);
      active_mob.constitution = Utility.clear_whitespace(txtCON.Text);
      active_mob.hitroll = Utility.clear_whitespace(txtHIT.Text);
      active_mob.damroll = Utility.clear_whitespace(txtDAM.Text);
      active_mob.armor_class = Utility.clear_whitespace(txtAC.Text);
      active_mob.magic_resistance = Utility.clear_whitespace(txtMR.Text);

      if (rdoMale.Checked)
        active_mob.gender = "Male";
      else
        active_mob.gender = "Female";

      if (chkNPC.Checked) active_mob.NPC = true; else active_mob.NPC = false;
      if (chkBard.Checked) active_mob.bard = true; else active_mob.bard = false;
      if (chkGuard.Checked) active_mob.guard = true; else active_mob.guard = false;
      if (chkSpellmaster.Checked) active_mob.spellmaster = true; else active_mob.spellmaster = false;
      if (chkSkillmaster.Checked) active_mob.skillmaster = true; else active_mob.skillmaster = false;
      if (chkBanker.Checked) active_mob.banker = true; else active_mob.banker = false;
      if (chkMobile.Checked) active_mob.mobile = true; else active_mob.mobile = false;
      if (chkBlocker.Checked) active_mob.blocker = true; else active_mob.blocker = false;
      
      active_mob.aggro = chkAggro.Checked;
      active_mob.undead = chkUndead.Checked;
      active_mob.sancd = chkSancd.Checked;
      active_mob.flying = chkFlying.Checked;
      active_mob.clergy = chkClergy.Checked;
      active_mob.teacher = chkTeacher.Checked;
      active_mob.herbalist = chkHerbalist.Checked;
      
      if (chkMerchant.Checked)

      {
        active_mob.merchant = true;
        active_mob.shop_name = Utility.clear_whitespace(txtShopName.Text);
      }

      else

      {
        active_mob.merchant = false;
        active_mob.shop_name = "";
      }

      if (chkBlocker.Checked)

      {
        active_mob.blocker = true;
        active_mob.btype = Utility.clear_whitespace(txtBType.Text);
        active_mob.bdir = Utility.clear_whitespace(txtBDir.Text);
      }

      else

      {
        active_mob.blocker = false;
        active_mob.btype = "";
        active_mob.bdir = "";
      }

      active_mob.load_items_freq = (ArrayList)load_items_freq.Clone();
      active_mob.wear_items_freq = (ArrayList)wear_items_freq.Clone();
      active_mob.skills = (ArrayList)skills.Clone();
      active_mob.spells = (ArrayList)spells.Clone();
      active_mob.actions = (ArrayList)actions.Clone();

      active_mob.litems = litems;
      active_mob.sitems = sitems;
      active_mob.witems = witems;

      saved = true;
    }

    private void update_litems()

    {
      string temp;

      lstLoadItems.Items.Clear();

      for (int i=0; i<litems.get_size(); i++)

      {
        temp  = litems.get_item(i).id;
        temp  = "[" + temp.PadLeft(5, '0') + "] ";
        temp += ((string)load_items_freq[i]) + " ";
        temp += litems.get_item(i).name;
        lstLoadItems.Items.Add(temp);
      }
    }

    private void update_witems()

    {
      string temp;

      lstWearItems.Items.Clear();

      for (int i=0; i<witems.get_size(); i++)

      {
        temp  = witems.get_item(i).id;
        temp  = "[" + temp.PadLeft(5, '0') + "] ";
        temp += ((string)wear_items_freq[i]) + " ";
        temp += witems.get_item(i).name;
        lstWearItems.Items.Add(temp);
      }
    }
    private void update_sitems()

    {
      string temp;

      lstSellItems.Items.Clear();

      for (int i=0; i<sitems.get_size(); i++)

      {
        temp  = sitems.get_item(i).id;
        temp  = "[" + temp.PadLeft(5, '0') + "] ";
        temp += sitems.get_item(i).name;
        lstSellItems.Items.Add(temp);
      }
    }

    private void update_skillslist()

    {
      lstSkills.Items.Clear();

      for (int i=0; i<skills.Count; i++)
        lstSkills.Items.Add(skills[i]);
    }
    private void update_spellslist()

    {
      lstSpells.Items.Clear();

      for (int i=0; i<spells.Count; i++)
        lstSpells.Items.Add(spells[i]);
    }

    private void update_actionslist()

    {
      lstActions.Items.Clear();

      for (int i=0; i<actions.Count; i++)
        lstActions.Items.Add(actions[i]);

      txtAction.Text = "";
      txtActionFreq.Text = "";
    }
    private string first(string s)

    {
      string temp;
      int i = s.IndexOf(" ");

      if (i == -1)
        return s;

      temp = s.Substring(0, i);
      return temp;
    }

    private string last(string s)

    {
      if (s == "") return "";
      if (s.IndexOf(" ") == -1) return "";

      int i = s.IndexOf(" ");
      if (i < 0) return "";

      return s.Substring(i+1, s.Length-i-1);
    }


    #region Windows Form Designer generated code
    private void InitializeComponent()
    {
      System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(frmMobEditor));
      this.cmdSave = new System.Windows.Forms.Button();
      this.cmdCancel = new System.Windows.Forms.Button();
      this.label1 = new System.Windows.Forms.Label();
      this.txtID = new System.Windows.Forms.TextBox();
      this.label2 = new System.Windows.Forms.Label();
      this.txtName = new System.Windows.Forms.TextBox();
      this.label3 = new System.Windows.Forms.Label();
      this.txtKeywords = new System.Windows.Forms.TextBox();
      this.label4 = new System.Windows.Forms.Label();
      this.txtRoomDesc = new System.Windows.Forms.TextBox();
      this.label5 = new System.Windows.Forms.Label();
      this.txtLookDesc = new System.Windows.Forms.TextBox();
      this.label6 = new System.Windows.Forms.Label();
      this.label7 = new System.Windows.Forms.Label();
      this.label8 = new System.Windows.Forms.Label();
      this.label9 = new System.Windows.Forms.Label();
      this.label10 = new System.Windows.Forms.Label();
      this.label11 = new System.Windows.Forms.Label();
      this.label12 = new System.Windows.Forms.Label();
      this.chkSpellmaster = new System.Windows.Forms.CheckBox();
      this.chkSkillmaster = new System.Windows.Forms.CheckBox();
      this.chkGuard = new System.Windows.Forms.CheckBox();
      this.chkMobile = new System.Windows.Forms.CheckBox();
      this.chkBard = new System.Windows.Forms.CheckBox();
      this.chkBanker = new System.Windows.Forms.CheckBox();
      this.chkMerchant = new System.Windows.Forms.CheckBox();
      this.chkNPC = new System.Windows.Forms.CheckBox();
      this.txtLevel = new System.Windows.Forms.TextBox();
      this.txtHitPoints = new System.Windows.Forms.TextBox();
      this.txtMana = new System.Windows.Forms.TextBox();
      this.txtExp = new System.Windows.Forms.TextBox();
      this.txtGold = new System.Windows.Forms.TextBox();
      this.txtAllign = new System.Windows.Forms.TextBox();
      this.txtSpeed = new System.Windows.Forms.TextBox();
      this.label13 = new System.Windows.Forms.Label();
      this.label14 = new System.Windows.Forms.Label();
      this.label15 = new System.Windows.Forms.Label();
      this.label16 = new System.Windows.Forms.Label();
      this.label17 = new System.Windows.Forms.Label();
      this.label18 = new System.Windows.Forms.Label();
      this.txtSTR = new System.Windows.Forms.TextBox();
      this.txtINT = new System.Windows.Forms.TextBox();
      this.txtWIS = new System.Windows.Forms.TextBox();
      this.txtDEX = new System.Windows.Forms.TextBox();
      this.txtCON = new System.Windows.Forms.TextBox();
      this.label19 = new System.Windows.Forms.Label();
      this.label20 = new System.Windows.Forms.Label();
      this.label21 = new System.Windows.Forms.Label();
      this.label22 = new System.Windows.Forms.Label();
      this.txtHIT = new System.Windows.Forms.TextBox();
      this.txtDAM = new System.Windows.Forms.TextBox();
      this.txtAC = new System.Windows.Forms.TextBox();
      this.txtMR = new System.Windows.Forms.TextBox();
      this.rdoFemale = new System.Windows.Forms.RadioButton();
      this.rdoMale = new System.Windows.Forms.RadioButton();
      this.lstActions = new System.Windows.Forms.ListBox();
      this.cmdAddAction = new System.Windows.Forms.Button();
      this.cmdRemoveAction = new System.Windows.Forms.Button();
      this.label23 = new System.Windows.Forms.Label();
      this.label24 = new System.Windows.Forms.Label();
      this.label25 = new System.Windows.Forms.Label();
      this.label26 = new System.Windows.Forms.Label();
      this.label27 = new System.Windows.Forms.Label();
      this.lstLoadItems = new System.Windows.Forms.ListBox();
      this.lstWearItems = new System.Windows.Forms.ListBox();
      this.lstSellItems = new System.Windows.Forms.ListBox();
      this.cmdAddLoadItem = new System.Windows.Forms.Button();
      this.cmdRemoveLoadItem = new System.Windows.Forms.Button();
      this.cmdAddWearItem = new System.Windows.Forms.Button();
      this.cmdRemoveWearItem = new System.Windows.Forms.Button();
      this.cmdAddSkill = new System.Windows.Forms.Button();
      this.cmdRemoveSkill = new System.Windows.Forms.Button();
      this.cmdAddSellItem = new System.Windows.Forms.Button();
      this.cmdRemoveSellItem = new System.Windows.Forms.Button();
      this.txtShopName = new System.Windows.Forms.TextBox();
      this.label28 = new System.Windows.Forms.Label();
      this.label29 = new System.Windows.Forms.Label();
      this.label30 = new System.Windows.Forms.Label();
      this.label31 = new System.Windows.Forms.Label();
      this.cmdRemoveSpell = new System.Windows.Forms.Button();
      this.cmdAddSpell = new System.Windows.Forms.Button();
      this.lstSkills = new System.Windows.Forms.ListBox();
      this.txtSkillName = new System.Windows.Forms.TextBox();
      this.txtSkillFreq = new System.Windows.Forms.TextBox();
      this.label32 = new System.Windows.Forms.Label();
      this.label33 = new System.Windows.Forms.Label();
      this.label34 = new System.Windows.Forms.Label();
      this.label35 = new System.Windows.Forms.Label();
      this.txtSpellFreq = new System.Windows.Forms.TextBox();
      this.txtSpellName = new System.Windows.Forms.TextBox();
      this.lstSpells = new System.Windows.Forms.ListBox();
      this.label36 = new System.Windows.Forms.Label();
      this.label37 = new System.Windows.Forms.Label();
      this.txtAction = new System.Windows.Forms.TextBox();
      this.txtActionFreq = new System.Windows.Forms.TextBox();
      this.lblBType = new System.Windows.Forms.Label();
      this.lblBDir = new System.Windows.Forms.Label();
      this.chkBlocker = new System.Windows.Forms.CheckBox();
      this.txtBType = new System.Windows.Forms.TextBox();
      this.txtBDir = new System.Windows.Forms.TextBox();
      this.chkClergy = new System.Windows.Forms.CheckBox();
      this.chkTeacher = new System.Windows.Forms.CheckBox();
      this.chkUndead = new System.Windows.Forms.CheckBox();
      this.chkHerbalist = new System.Windows.Forms.CheckBox();
      this.chkSancd = new System.Windows.Forms.CheckBox();
      this.chkFlying = new System.Windows.Forms.CheckBox();
      this.chkAggro = new System.Windows.Forms.CheckBox();
      this.SuspendLayout();
      // 
      // cmdSave
      // 
      this.cmdSave.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdSave.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdSave.Location = new System.Drawing.Point(346, 544);
      this.cmdSave.Name = "cmdSave";
      this.cmdSave.TabIndex = 0;
      this.cmdSave.Text = "&Save";
      this.cmdSave.Click += new System.EventHandler(this.cmdSave_Click);
      // 
      // cmdCancel
      // 
      this.cmdCancel.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdCancel.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdCancel.Location = new System.Drawing.Point(450, 544);
      this.cmdCancel.Name = "cmdCancel";
      this.cmdCancel.TabIndex = 1;
      this.cmdCancel.Text = "&Cancel";
      this.cmdCancel.Click += new System.EventHandler(this.cmdCancel_Click);
      // 
      // label1
      // 
      this.label1.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label1.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label1.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label1.Location = new System.Drawing.Point(8, 8);
      this.label1.Name = "label1";
      this.label1.Size = new System.Drawing.Size(72, 16);
      this.label1.TabIndex = 2;
      this.label1.Text = "ID:";
      this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtID
      // 
      this.txtID.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtID.Location = new System.Drawing.Point(88, 8);
      this.txtID.Name = "txtID";
      this.txtID.Size = new System.Drawing.Size(104, 20);
      this.txtID.TabIndex = 3;
      this.txtID.Text = "";
      // 
      // label2
      // 
      this.label2.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label2.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label2.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label2.Location = new System.Drawing.Point(8, 32);
      this.label2.Name = "label2";
      this.label2.Size = new System.Drawing.Size(72, 16);
      this.label2.TabIndex = 4;
      this.label2.Text = "Name:";
      this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtName
      // 
      this.txtName.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtName.Location = new System.Drawing.Point(88, 32);
      this.txtName.Name = "txtName";
      this.txtName.Size = new System.Drawing.Size(280, 20);
      this.txtName.TabIndex = 5;
      this.txtName.Text = "";
      // 
      // label3
      // 
      this.label3.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label3.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label3.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label3.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label3.Location = new System.Drawing.Point(8, 56);
      this.label3.Name = "label3";
      this.label3.Size = new System.Drawing.Size(72, 16);
      this.label3.TabIndex = 6;
      this.label3.Text = "Keywords:";
      this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtKeywords
      // 
      this.txtKeywords.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtKeywords.Location = new System.Drawing.Point(88, 56);
      this.txtKeywords.Name = "txtKeywords";
      this.txtKeywords.Size = new System.Drawing.Size(344, 20);
      this.txtKeywords.TabIndex = 7;
      this.txtKeywords.Text = "";
      // 
      // label4
      // 
      this.label4.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label4.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label4.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label4.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label4.Location = new System.Drawing.Point(8, 80);
      this.label4.Name = "label4";
      this.label4.Size = new System.Drawing.Size(72, 16);
      this.label4.TabIndex = 8;
      this.label4.Text = "RDesc:";
      this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtRoomDesc
      // 
      this.txtRoomDesc.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtRoomDesc.Location = new System.Drawing.Point(88, 80);
      this.txtRoomDesc.Name = "txtRoomDesc";
      this.txtRoomDesc.Size = new System.Drawing.Size(344, 20);
      this.txtRoomDesc.TabIndex = 9;
      this.txtRoomDesc.Text = "";
      // 
      // label5
      // 
      this.label5.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label5.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label5.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label5.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label5.Location = new System.Drawing.Point(8, 104);
      this.label5.Name = "label5";
      this.label5.Size = new System.Drawing.Size(72, 16);
      this.label5.TabIndex = 10;
      this.label5.Text = "LDesc:";
      this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtLookDesc
      // 
      this.txtLookDesc.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtLookDesc.Location = new System.Drawing.Point(88, 104);
      this.txtLookDesc.Multiline = true;
      this.txtLookDesc.Name = "txtLookDesc";
      this.txtLookDesc.Size = new System.Drawing.Size(344, 56);
      this.txtLookDesc.TabIndex = 11;
      this.txtLookDesc.Text = "";
      // 
      // label6
      // 
      this.label6.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label6.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label6.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label6.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label6.Location = new System.Drawing.Point(8, 192);
      this.label6.Name = "label6";
      this.label6.Size = new System.Drawing.Size(72, 16);
      this.label6.TabIndex = 12;
      this.label6.Text = "Level:";
      this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label7
      // 
      this.label7.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label7.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label7.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label7.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label7.Location = new System.Drawing.Point(8, 216);
      this.label7.Name = "label7";
      this.label7.Size = new System.Drawing.Size(72, 16);
      this.label7.TabIndex = 13;
      this.label7.Text = "HitPoints:";
      this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label8
      // 
      this.label8.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label8.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label8.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label8.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label8.Location = new System.Drawing.Point(8, 240);
      this.label8.Name = "label8";
      this.label8.Size = new System.Drawing.Size(72, 16);
      this.label8.TabIndex = 14;
      this.label8.Text = "Mana:";
      this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label9
      // 
      this.label9.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label9.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label9.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label9.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label9.Location = new System.Drawing.Point(8, 264);
      this.label9.Name = "label9";
      this.label9.Size = new System.Drawing.Size(72, 16);
      this.label9.TabIndex = 15;
      this.label9.Text = "Exp:";
      this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label10
      // 
      this.label10.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label10.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label10.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label10.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label10.Location = new System.Drawing.Point(8, 288);
      this.label10.Name = "label10";
      this.label10.Size = new System.Drawing.Size(72, 16);
      this.label10.TabIndex = 16;
      this.label10.Text = "Gold:";
      this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label11
      // 
      this.label11.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label11.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label11.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label11.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label11.Location = new System.Drawing.Point(8, 312);
      this.label11.Name = "label11";
      this.label11.Size = new System.Drawing.Size(72, 16);
      this.label11.TabIndex = 17;
      this.label11.Text = "Align:";
      this.label11.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label12
      // 
      this.label12.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label12.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label12.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label12.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label12.Location = new System.Drawing.Point(8, 336);
      this.label12.Name = "label12";
      this.label12.Size = new System.Drawing.Size(72, 16);
      this.label12.TabIndex = 18;
      this.label12.Text = "Speed:";
      this.label12.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // chkSpellmaster
      // 
      this.chkSpellmaster.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkSpellmaster.BackgroundImage")));
      this.chkSpellmaster.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkSpellmaster.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkSpellmaster.Location = new System.Drawing.Point(440, 144);
      this.chkSpellmaster.Name = "chkSpellmaster";
      this.chkSpellmaster.Size = new System.Drawing.Size(104, 16);
      this.chkSpellmaster.TabIndex = 7;
      this.chkSpellmaster.Text = "SPELLMASTER";
      // 
      // chkSkillmaster
      // 
      this.chkSkillmaster.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkSkillmaster.BackgroundImage")));
      this.chkSkillmaster.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkSkillmaster.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkSkillmaster.Location = new System.Drawing.Point(440, 128);
      this.chkSkillmaster.Name = "chkSkillmaster";
      this.chkSkillmaster.Size = new System.Drawing.Size(104, 16);
      this.chkSkillmaster.TabIndex = 6;
      this.chkSkillmaster.Text = "SKILLMASTER";
      // 
      // chkGuard
      // 
      this.chkGuard.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkGuard.BackgroundImage")));
      this.chkGuard.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkGuard.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkGuard.Location = new System.Drawing.Point(440, 112);
      this.chkGuard.Name = "chkGuard";
      this.chkGuard.Size = new System.Drawing.Size(80, 16);
      this.chkGuard.TabIndex = 5;
      this.chkGuard.Text = "GUARD";
      // 
      // chkMobile
      // 
      this.chkMobile.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkMobile.BackgroundImage")));
      this.chkMobile.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkMobile.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkMobile.Location = new System.Drawing.Point(440, 96);
      this.chkMobile.Name = "chkMobile";
      this.chkMobile.Size = new System.Drawing.Size(80, 16);
      this.chkMobile.TabIndex = 4;
      this.chkMobile.Text = "MOBILE";
      // 
      // chkBard
      // 
      this.chkBard.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkBard.BackgroundImage")));
      this.chkBard.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkBard.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkBard.Location = new System.Drawing.Point(440, 80);
      this.chkBard.Name = "chkBard";
      this.chkBard.Size = new System.Drawing.Size(80, 16);
      this.chkBard.TabIndex = 3;
      this.chkBard.Text = "BARD";
      // 
      // chkBanker
      // 
      this.chkBanker.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkBanker.BackgroundImage")));
      this.chkBanker.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkBanker.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkBanker.Location = new System.Drawing.Point(440, 64);
      this.chkBanker.Name = "chkBanker";
      this.chkBanker.Size = new System.Drawing.Size(80, 16);
      this.chkBanker.TabIndex = 2;
      this.chkBanker.Text = "BANKER";
      // 
      // chkMerchant
      // 
      this.chkMerchant.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkMerchant.BackgroundImage")));
      this.chkMerchant.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkMerchant.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkMerchant.Location = new System.Drawing.Point(440, 48);
      this.chkMerchant.Name = "chkMerchant";
      this.chkMerchant.Size = new System.Drawing.Size(80, 16);
      this.chkMerchant.TabIndex = 1;
      this.chkMerchant.Text = "MERCHANT";
      this.chkMerchant.CheckedChanged += new System.EventHandler(this.chkMerchant_CheckedChanged);
      // 
      // chkNPC
      // 
      this.chkNPC.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNPC.BackgroundImage")));
      this.chkNPC.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNPC.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNPC.Location = new System.Drawing.Point(440, 32);
      this.chkNPC.Name = "chkNPC";
      this.chkNPC.Size = new System.Drawing.Size(64, 16);
      this.chkNPC.TabIndex = 0;
      this.chkNPC.Text = "NPC";
      // 
      // txtLevel
      // 
      this.txtLevel.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtLevel.Location = new System.Drawing.Point(88, 192);
      this.txtLevel.Name = "txtLevel";
      this.txtLevel.Size = new System.Drawing.Size(64, 20);
      this.txtLevel.TabIndex = 21;
      this.txtLevel.Text = "";
      // 
      // txtHitPoints
      // 
      this.txtHitPoints.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtHitPoints.Location = new System.Drawing.Point(88, 216);
      this.txtHitPoints.Name = "txtHitPoints";
      this.txtHitPoints.Size = new System.Drawing.Size(64, 20);
      this.txtHitPoints.TabIndex = 22;
      this.txtHitPoints.Text = "";
      // 
      // txtMana
      // 
      this.txtMana.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtMana.Location = new System.Drawing.Point(88, 240);
      this.txtMana.Name = "txtMana";
      this.txtMana.Size = new System.Drawing.Size(64, 20);
      this.txtMana.TabIndex = 23;
      this.txtMana.Text = "";
      // 
      // txtExp
      // 
      this.txtExp.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtExp.Location = new System.Drawing.Point(88, 264);
      this.txtExp.Name = "txtExp";
      this.txtExp.Size = new System.Drawing.Size(64, 20);
      this.txtExp.TabIndex = 24;
      this.txtExp.Text = "";
      // 
      // txtGold
      // 
      this.txtGold.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtGold.Location = new System.Drawing.Point(88, 288);
      this.txtGold.Name = "txtGold";
      this.txtGold.Size = new System.Drawing.Size(64, 20);
      this.txtGold.TabIndex = 25;
      this.txtGold.Text = "";
      // 
      // txtAllign
      // 
      this.txtAllign.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtAllign.Location = new System.Drawing.Point(88, 312);
      this.txtAllign.Name = "txtAllign";
      this.txtAllign.Size = new System.Drawing.Size(64, 20);
      this.txtAllign.TabIndex = 26;
      this.txtAllign.Text = "";
      // 
      // txtSpeed
      // 
      this.txtSpeed.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtSpeed.Location = new System.Drawing.Point(88, 336);
      this.txtSpeed.Name = "txtSpeed";
      this.txtSpeed.Size = new System.Drawing.Size(64, 20);
      this.txtSpeed.TabIndex = 27;
      this.txtSpeed.Text = "";
      // 
      // label13
      // 
      this.label13.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label13.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label13.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label13.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label13.Location = new System.Drawing.Point(440, 168);
      this.label13.Name = "label13";
      this.label13.Size = new System.Drawing.Size(72, 16);
      this.label13.TabIndex = 28;
      this.label13.Text = "Shop Name";
      this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label14
      // 
      this.label14.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label14.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label14.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label14.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label14.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label14.Location = new System.Drawing.Point(160, 192);
      this.label14.Name = "label14";
      this.label14.Size = new System.Drawing.Size(48, 16);
      this.label14.TabIndex = 29;
      this.label14.Text = "STR:";
      this.label14.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label15
      // 
      this.label15.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label15.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label15.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label15.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label15.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label15.Location = new System.Drawing.Point(160, 216);
      this.label15.Name = "label15";
      this.label15.Size = new System.Drawing.Size(48, 16);
      this.label15.TabIndex = 30;
      this.label15.Text = "INT:";
      this.label15.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label16
      // 
      this.label16.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label16.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label16.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label16.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label16.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label16.Location = new System.Drawing.Point(160, 240);
      this.label16.Name = "label16";
      this.label16.Size = new System.Drawing.Size(48, 16);
      this.label16.TabIndex = 31;
      this.label16.Text = "WIS:";
      this.label16.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label17
      // 
      this.label17.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label17.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label17.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label17.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label17.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label17.Location = new System.Drawing.Point(160, 264);
      this.label17.Name = "label17";
      this.label17.Size = new System.Drawing.Size(48, 16);
      this.label17.TabIndex = 32;
      this.label17.Text = "DEX:";
      this.label17.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label18
      // 
      this.label18.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label18.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label18.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label18.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label18.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label18.Location = new System.Drawing.Point(160, 288);
      this.label18.Name = "label18";
      this.label18.Size = new System.Drawing.Size(48, 16);
      this.label18.TabIndex = 33;
      this.label18.Text = "CON:";
      this.label18.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtSTR
      // 
      this.txtSTR.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtSTR.Location = new System.Drawing.Point(216, 192);
      this.txtSTR.Name = "txtSTR";
      this.txtSTR.Size = new System.Drawing.Size(64, 20);
      this.txtSTR.TabIndex = 34;
      this.txtSTR.Text = "";
      // 
      // txtINT
      // 
      this.txtINT.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtINT.Location = new System.Drawing.Point(216, 216);
      this.txtINT.Name = "txtINT";
      this.txtINT.Size = new System.Drawing.Size(64, 20);
      this.txtINT.TabIndex = 35;
      this.txtINT.Text = "";
      // 
      // txtWIS
      // 
      this.txtWIS.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtWIS.Location = new System.Drawing.Point(216, 240);
      this.txtWIS.Name = "txtWIS";
      this.txtWIS.Size = new System.Drawing.Size(64, 20);
      this.txtWIS.TabIndex = 36;
      this.txtWIS.Text = "";
      // 
      // txtDEX
      // 
      this.txtDEX.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtDEX.Location = new System.Drawing.Point(216, 264);
      this.txtDEX.Name = "txtDEX";
      this.txtDEX.Size = new System.Drawing.Size(64, 20);
      this.txtDEX.TabIndex = 37;
      this.txtDEX.Text = "";
      // 
      // txtCON
      // 
      this.txtCON.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtCON.Location = new System.Drawing.Point(216, 288);
      this.txtCON.Name = "txtCON";
      this.txtCON.Size = new System.Drawing.Size(64, 20);
      this.txtCON.TabIndex = 38;
      this.txtCON.Text = "";
      // 
      // label19
      // 
      this.label19.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label19.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label19.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label19.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label19.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label19.Location = new System.Drawing.Point(288, 192);
      this.label19.Name = "label19";
      this.label19.Size = new System.Drawing.Size(48, 16);
      this.label19.TabIndex = 39;
      this.label19.Text = "HIT:";
      this.label19.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label20
      // 
      this.label20.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label20.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label20.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label20.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label20.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label20.Location = new System.Drawing.Point(288, 216);
      this.label20.Name = "label20";
      this.label20.Size = new System.Drawing.Size(48, 16);
      this.label20.TabIndex = 40;
      this.label20.Text = "DAM:";
      this.label20.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label21
      // 
      this.label21.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label21.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label21.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label21.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label21.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label21.Location = new System.Drawing.Point(160, 312);
      this.label21.Name = "label21";
      this.label21.Size = new System.Drawing.Size(48, 16);
      this.label21.TabIndex = 41;
      this.label21.Text = "AC:";
      this.label21.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label22
      // 
      this.label22.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label22.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label22.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label22.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label22.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label22.Location = new System.Drawing.Point(160, 336);
      this.label22.Name = "label22";
      this.label22.Size = new System.Drawing.Size(48, 16);
      this.label22.TabIndex = 42;
      this.label22.Text = "MR:";
      this.label22.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtHIT
      // 
      this.txtHIT.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtHIT.Location = new System.Drawing.Point(344, 192);
      this.txtHIT.Name = "txtHIT";
      this.txtHIT.Size = new System.Drawing.Size(88, 20);
      this.txtHIT.TabIndex = 43;
      this.txtHIT.Text = "";
      // 
      // txtDAM
      // 
      this.txtDAM.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtDAM.Location = new System.Drawing.Point(344, 216);
      this.txtDAM.Name = "txtDAM";
      this.txtDAM.Size = new System.Drawing.Size(88, 20);
      this.txtDAM.TabIndex = 44;
      this.txtDAM.Text = "";
      // 
      // txtAC
      // 
      this.txtAC.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtAC.Location = new System.Drawing.Point(216, 312);
      this.txtAC.Name = "txtAC";
      this.txtAC.Size = new System.Drawing.Size(64, 20);
      this.txtAC.TabIndex = 45;
      this.txtAC.Text = "";
      // 
      // txtMR
      // 
      this.txtMR.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtMR.Location = new System.Drawing.Point(216, 336);
      this.txtMR.Name = "txtMR";
      this.txtMR.Size = new System.Drawing.Size(64, 20);
      this.txtMR.TabIndex = 46;
      this.txtMR.Text = "";
      // 
      // rdoFemale
      // 
      this.rdoFemale.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("rdoFemale.BackgroundImage")));
      this.rdoFemale.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.rdoFemale.Location = new System.Drawing.Point(368, 272);
      this.rdoFemale.Name = "rdoFemale";
      this.rdoFemale.Size = new System.Drawing.Size(64, 16);
      this.rdoFemale.TabIndex = 1;
      this.rdoFemale.Text = "Female";
      // 
      // rdoMale
      // 
      this.rdoMale.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("rdoMale.BackgroundImage")));
      this.rdoMale.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.rdoMale.Location = new System.Drawing.Point(288, 272);
      this.rdoMale.Name = "rdoMale";
      this.rdoMale.Size = new System.Drawing.Size(56, 16);
      this.rdoMale.TabIndex = 0;
      this.rdoMale.Text = "Male";
      // 
      // lstActions
      // 
      this.lstActions.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstActions.Location = new System.Drawing.Point(440, 272);
      this.lstActions.Name = "lstActions";
      this.lstActions.Size = new System.Drawing.Size(208, 158);
      this.lstActions.TabIndex = 48;
      this.lstActions.SelectedIndexChanged += new System.EventHandler(this.lstActions_SelectedIndexChanged);
      // 
      // cmdAddAction
      // 
      this.cmdAddAction.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddAction.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddAction.Location = new System.Drawing.Point(440, 488);
      this.cmdAddAction.Name = "cmdAddAction";
      this.cmdAddAction.TabIndex = 49;
      this.cmdAddAction.Text = "Add";
      this.cmdAddAction.Click += new System.EventHandler(this.cmdAddAction_Click);
      // 
      // cmdRemoveAction
      // 
      this.cmdRemoveAction.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveAction.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveAction.Location = new System.Drawing.Point(568, 488);
      this.cmdRemoveAction.Name = "cmdRemoveAction";
      this.cmdRemoveAction.TabIndex = 50;
      this.cmdRemoveAction.Text = "Remove";
      this.cmdRemoveAction.Click += new System.EventHandler(this.cmdRemoveAction_Click);
      // 
      // label23
      // 
      this.label23.BackColor = System.Drawing.Color.Lavender;
      this.label23.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label23.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label23.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label23.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label23.Location = new System.Drawing.Point(440, 248);
      this.label23.Name = "label23";
      this.label23.Size = new System.Drawing.Size(208, 16);
      this.label23.TabIndex = 53;
      this.label23.Text = "Actions";
      this.label23.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label24
      // 
      this.label24.BackColor = System.Drawing.Color.Lavender;
      this.label24.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label24.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label24.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label24.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label24.Location = new System.Drawing.Point(8, 368);
      this.label24.Name = "label24";
      this.label24.Size = new System.Drawing.Size(136, 16);
      this.label24.TabIndex = 54;
      this.label24.Text = "Load Items";
      this.label24.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label25
      // 
      this.label25.BackColor = System.Drawing.Color.Lavender;
      this.label25.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label25.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label25.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label25.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label25.Location = new System.Drawing.Point(152, 368);
      this.label25.Name = "label25";
      this.label25.Size = new System.Drawing.Size(136, 16);
      this.label25.TabIndex = 55;
      this.label25.Text = "Wear Items";
      this.label25.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label26
      // 
      this.label26.BackColor = System.Drawing.Color.Lavender;
      this.label26.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label26.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label26.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label26.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label26.Location = new System.Drawing.Point(296, 368);
      this.label26.Name = "label26";
      this.label26.Size = new System.Drawing.Size(136, 16);
      this.label26.TabIndex = 56;
      this.label26.Text = "Sell Items";
      this.label26.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label27
      // 
      this.label27.BackColor = System.Drawing.Color.Lavender;
      this.label27.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label27.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label27.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label27.Location = new System.Drawing.Point(656, 8);
      this.label27.Name = "label27";
      this.label27.Size = new System.Drawing.Size(208, 16);
      this.label27.TabIndex = 57;
      this.label27.Text = "Skills";
      this.label27.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // lstLoadItems
      // 
      this.lstLoadItems.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstLoadItems.Location = new System.Drawing.Point(8, 392);
      this.lstLoadItems.Name = "lstLoadItems";
      this.lstLoadItems.Size = new System.Drawing.Size(136, 93);
      this.lstLoadItems.TabIndex = 59;
      // 
      // lstWearItems
      // 
      this.lstWearItems.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstWearItems.Location = new System.Drawing.Point(152, 392);
      this.lstWearItems.Name = "lstWearItems";
      this.lstWearItems.Size = new System.Drawing.Size(136, 93);
      this.lstWearItems.TabIndex = 60;
      // 
      // lstSellItems
      // 
      this.lstSellItems.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstSellItems.Location = new System.Drawing.Point(296, 392);
      this.lstSellItems.Name = "lstSellItems";
      this.lstSellItems.Size = new System.Drawing.Size(136, 93);
      this.lstSellItems.TabIndex = 61;
      // 
      // cmdAddLoadItem
      // 
      this.cmdAddLoadItem.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddLoadItem.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddLoadItem.Location = new System.Drawing.Point(8, 488);
      this.cmdAddLoadItem.Name = "cmdAddLoadItem";
      this.cmdAddLoadItem.TabIndex = 62;
      this.cmdAddLoadItem.Text = "Add";
      this.cmdAddLoadItem.Click += new System.EventHandler(this.cmdAddLoadItem_Click);
      // 
      // cmdRemoveLoadItem
      // 
      this.cmdRemoveLoadItem.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveLoadItem.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveLoadItem.Location = new System.Drawing.Point(8, 512);
      this.cmdRemoveLoadItem.Name = "cmdRemoveLoadItem";
      this.cmdRemoveLoadItem.TabIndex = 63;
      this.cmdRemoveLoadItem.Text = "Remove";
      this.cmdRemoveLoadItem.Click += new System.EventHandler(this.cmdRemoveLoadItem_Click);
      // 
      // cmdAddWearItem
      // 
      this.cmdAddWearItem.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddWearItem.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddWearItem.Location = new System.Drawing.Point(152, 488);
      this.cmdAddWearItem.Name = "cmdAddWearItem";
      this.cmdAddWearItem.TabIndex = 64;
      this.cmdAddWearItem.Text = "Add";
      this.cmdAddWearItem.Click += new System.EventHandler(this.cmdAddWearItem_Click);
      // 
      // cmdRemoveWearItem
      // 
      this.cmdRemoveWearItem.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveWearItem.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveWearItem.Location = new System.Drawing.Point(152, 512);
      this.cmdRemoveWearItem.Name = "cmdRemoveWearItem";
      this.cmdRemoveWearItem.TabIndex = 65;
      this.cmdRemoveWearItem.Text = "Remove";
      this.cmdRemoveWearItem.Click += new System.EventHandler(this.cmdRemoveWearItem_Click);
      // 
      // cmdAddSkill
      // 
      this.cmdAddSkill.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddSkill.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddSkill.Location = new System.Drawing.Point(656, 216);
      this.cmdAddSkill.Name = "cmdAddSkill";
      this.cmdAddSkill.TabIndex = 66;
      this.cmdAddSkill.Text = "Add";
      this.cmdAddSkill.Click += new System.EventHandler(this.cmdAddSkill_Click);
      // 
      // cmdRemoveSkill
      // 
      this.cmdRemoveSkill.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveSkill.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveSkill.Location = new System.Drawing.Point(792, 216);
      this.cmdRemoveSkill.Name = "cmdRemoveSkill";
      this.cmdRemoveSkill.TabIndex = 67;
      this.cmdRemoveSkill.Text = "Remove";
      this.cmdRemoveSkill.Click += new System.EventHandler(this.cmdRemoveSkill_Click);
      // 
      // cmdAddSellItem
      // 
      this.cmdAddSellItem.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddSellItem.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddSellItem.Location = new System.Drawing.Point(296, 488);
      this.cmdAddSellItem.Name = "cmdAddSellItem";
      this.cmdAddSellItem.TabIndex = 68;
      this.cmdAddSellItem.Text = "Add";
      this.cmdAddSellItem.Click += new System.EventHandler(this.cmdAddSellItem_Click);
      // 
      // cmdRemoveSellItem
      // 
      this.cmdRemoveSellItem.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveSellItem.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveSellItem.Location = new System.Drawing.Point(296, 512);
      this.cmdRemoveSellItem.Name = "cmdRemoveSellItem";
      this.cmdRemoveSellItem.TabIndex = 69;
      this.cmdRemoveSellItem.Text = "Remove";
      this.cmdRemoveSellItem.Click += new System.EventHandler(this.cmdRemoveSellItem_Click);
      // 
      // txtShopName
      // 
      this.txtShopName.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtShopName.Location = new System.Drawing.Point(520, 166);
      this.txtShopName.Name = "txtShopName";
      this.txtShopName.Size = new System.Drawing.Size(128, 20);
      this.txtShopName.TabIndex = 70;
      this.txtShopName.Text = "";
      // 
      // label28
      // 
      this.label28.BackColor = System.Drawing.Color.Lavender;
      this.label28.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label28.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label28.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label28.Location = new System.Drawing.Point(8, 168);
      this.label28.Name = "label28";
      this.label28.Size = new System.Drawing.Size(424, 16);
      this.label28.TabIndex = 71;
      this.label28.Text = "Stats";
      this.label28.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label29
      // 
      this.label29.BackColor = System.Drawing.Color.Lavender;
      this.label29.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label29.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label29.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label29.Location = new System.Drawing.Point(440, 8);
      this.label29.Name = "label29";
      this.label29.Size = new System.Drawing.Size(208, 16);
      this.label29.TabIndex = 72;
      this.label29.Text = "Flags";
      this.label29.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label30
      // 
      this.label30.BackColor = System.Drawing.Color.Lavender;
      this.label30.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label30.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label30.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label30.Location = new System.Drawing.Point(288, 248);
      this.label30.Name = "label30";
      this.label30.Size = new System.Drawing.Size(144, 16);
      this.label30.TabIndex = 73;
      this.label30.Text = "Gender";
      this.label30.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label31
      // 
      this.label31.BackColor = System.Drawing.Color.Lavender;
      this.label31.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label31.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label31.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label31.Location = new System.Drawing.Point(656, 248);
      this.label31.Name = "label31";
      this.label31.Size = new System.Drawing.Size(208, 16);
      this.label31.TabIndex = 75;
      this.label31.Text = "Spells";
      this.label31.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // cmdRemoveSpell
      // 
      this.cmdRemoveSpell.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveSpell.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveSpell.Location = new System.Drawing.Point(792, 488);
      this.cmdRemoveSpell.Name = "cmdRemoveSpell";
      this.cmdRemoveSpell.TabIndex = 78;
      this.cmdRemoveSpell.Text = "Remove";
      this.cmdRemoveSpell.Click += new System.EventHandler(this.cmdRemoveSpell_Click);
      // 
      // cmdAddSpell
      // 
      this.cmdAddSpell.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddSpell.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddSpell.Location = new System.Drawing.Point(656, 488);
      this.cmdAddSpell.Name = "cmdAddSpell";
      this.cmdAddSpell.TabIndex = 77;
      this.cmdAddSpell.Text = "Add";
      this.cmdAddSpell.Click += new System.EventHandler(this.cmdAddSpell_Click);
      // 
      // lstSkills
      // 
      this.lstSkills.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstSkills.Location = new System.Drawing.Point(656, 32);
      this.lstSkills.Name = "lstSkills";
      this.lstSkills.Size = new System.Drawing.Size(208, 132);
      this.lstSkills.TabIndex = 79;
      this.lstSkills.SelectedIndexChanged += new System.EventHandler(this.lstSkills_SelectedIndexChanged);
      // 
      // txtSkillName
      // 
      this.txtSkillName.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtSkillName.Location = new System.Drawing.Point(712, 168);
      this.txtSkillName.Name = "txtSkillName";
      this.txtSkillName.Size = new System.Drawing.Size(152, 20);
      this.txtSkillName.TabIndex = 80;
      this.txtSkillName.Text = "";
      // 
      // txtSkillFreq
      // 
      this.txtSkillFreq.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtSkillFreq.Location = new System.Drawing.Point(712, 192);
      this.txtSkillFreq.Name = "txtSkillFreq";
      this.txtSkillFreq.Size = new System.Drawing.Size(96, 20);
      this.txtSkillFreq.TabIndex = 81;
      this.txtSkillFreq.Text = "";
      // 
      // label32
      // 
      this.label32.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label32.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label32.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label32.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label32.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label32.Location = new System.Drawing.Point(656, 168);
      this.label32.Name = "label32";
      this.label32.Size = new System.Drawing.Size(48, 16);
      this.label32.TabIndex = 82;
      this.label32.Text = "Name:";
      this.label32.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label33
      // 
      this.label33.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label33.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label33.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label33.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label33.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label33.Location = new System.Drawing.Point(656, 192);
      this.label33.Name = "label33";
      this.label33.Size = new System.Drawing.Size(48, 16);
      this.label33.TabIndex = 83;
      this.label33.Text = "Freq:";
      this.label33.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label34
      // 
      this.label34.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label34.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label34.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label34.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label34.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label34.Location = new System.Drawing.Point(656, 464);
      this.label34.Name = "label34";
      this.label34.Size = new System.Drawing.Size(48, 16);
      this.label34.TabIndex = 87;
      this.label34.Text = "Freq:";
      this.label34.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label35
      // 
      this.label35.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label35.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label35.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label35.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label35.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label35.Location = new System.Drawing.Point(656, 440);
      this.label35.Name = "label35";
      this.label35.Size = new System.Drawing.Size(48, 16);
      this.label35.TabIndex = 86;
      this.label35.Text = "Name:";
      this.label35.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtSpellFreq
      // 
      this.txtSpellFreq.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtSpellFreq.Location = new System.Drawing.Point(712, 464);
      this.txtSpellFreq.Name = "txtSpellFreq";
      this.txtSpellFreq.Size = new System.Drawing.Size(96, 20);
      this.txtSpellFreq.TabIndex = 85;
      this.txtSpellFreq.Text = "";
      // 
      // txtSpellName
      // 
      this.txtSpellName.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtSpellName.Location = new System.Drawing.Point(712, 440);
      this.txtSpellName.Name = "txtSpellName";
      this.txtSpellName.Size = new System.Drawing.Size(152, 20);
      this.txtSpellName.TabIndex = 84;
      this.txtSpellName.Text = "";
      // 
      // lstSpells
      // 
      this.lstSpells.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstSpells.Location = new System.Drawing.Point(656, 272);
      this.lstSpells.Name = "lstSpells";
      this.lstSpells.Size = new System.Drawing.Size(208, 158);
      this.lstSpells.TabIndex = 88;
      this.lstSpells.SelectedIndexChanged += new System.EventHandler(this.lstSpells_SelectedIndexChanged);
      // 
      // label36
      // 
      this.label36.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label36.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label36.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label36.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label36.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label36.Location = new System.Drawing.Point(440, 440);
      this.label36.Name = "label36";
      this.label36.Size = new System.Drawing.Size(48, 16);
      this.label36.TabIndex = 89;
      this.label36.Text = "Name:";
      this.label36.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label37
      // 
      this.label37.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label37.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label37.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label37.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label37.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label37.Location = new System.Drawing.Point(440, 464);
      this.label37.Name = "label37";
      this.label37.Size = new System.Drawing.Size(48, 16);
      this.label37.TabIndex = 90;
      this.label37.Text = "Freq:";
      this.label37.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtAction
      // 
      this.txtAction.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtAction.Location = new System.Drawing.Point(496, 440);
      this.txtAction.Name = "txtAction";
      this.txtAction.Size = new System.Drawing.Size(152, 20);
      this.txtAction.TabIndex = 91;
      this.txtAction.Text = "";
      // 
      // txtActionFreq
      // 
      this.txtActionFreq.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtActionFreq.Location = new System.Drawing.Point(496, 464);
      this.txtActionFreq.Name = "txtActionFreq";
      this.txtActionFreq.Size = new System.Drawing.Size(96, 20);
      this.txtActionFreq.TabIndex = 92;
      this.txtActionFreq.Text = "";
      // 
      // lblBType
      // 
      this.lblBType.BackColor = System.Drawing.Color.AntiqueWhite;
      this.lblBType.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lblBType.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.lblBType.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.lblBType.Location = new System.Drawing.Point(440, 192);
      this.lblBType.Name = "lblBType";
      this.lblBType.Size = new System.Drawing.Size(104, 16);
      this.lblBType.TabIndex = 93;
      this.lblBType.Text = "Blocker Type:";
      this.lblBType.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // lblBDir
      // 
      this.lblBDir.BackColor = System.Drawing.Color.AntiqueWhite;
      this.lblBDir.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lblBDir.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.lblBDir.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.lblBDir.Location = new System.Drawing.Point(440, 216);
      this.lblBDir.Name = "lblBDir";
      this.lblBDir.Size = new System.Drawing.Size(104, 16);
      this.lblBDir.TabIndex = 94;
      this.lblBDir.Text = "Blocker Dir:";
      this.lblBDir.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // chkBlocker
      // 
      this.chkBlocker.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkBlocker.BackgroundImage")));
      this.chkBlocker.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkBlocker.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkBlocker.Location = new System.Drawing.Point(552, 48);
      this.chkBlocker.Name = "chkBlocker";
      this.chkBlocker.Size = new System.Drawing.Size(72, 16);
      this.chkBlocker.TabIndex = 95;
      this.chkBlocker.Text = "BLOCKER";
      this.chkBlocker.CheckedChanged += new System.EventHandler(this.chkBlocker_CheckedChanged);
      // 
      // txtBType
      // 
      this.txtBType.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtBType.Location = new System.Drawing.Point(552, 190);
      this.txtBType.Name = "txtBType";
      this.txtBType.Size = new System.Drawing.Size(96, 20);
      this.txtBType.TabIndex = 96;
      this.txtBType.Text = "";
      // 
      // txtBDir
      // 
      this.txtBDir.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtBDir.Location = new System.Drawing.Point(552, 214);
      this.txtBDir.Name = "txtBDir";
      this.txtBDir.Size = new System.Drawing.Size(96, 20);
      this.txtBDir.TabIndex = 97;
      this.txtBDir.Text = "";
      // 
      // chkClergy
      // 
      this.chkClergy.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkClergy.BackgroundImage")));
      this.chkClergy.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkClergy.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkClergy.Location = new System.Drawing.Point(552, 64);
      this.chkClergy.Name = "chkClergy";
      this.chkClergy.Size = new System.Drawing.Size(88, 16);
      this.chkClergy.TabIndex = 98;
      this.chkClergy.Text = "CLERGY";
      // 
      // chkTeacher
      // 
      this.chkTeacher.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkTeacher.BackgroundImage")));
      this.chkTeacher.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkTeacher.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkTeacher.Location = new System.Drawing.Point(552, 80);
      this.chkTeacher.Name = "chkTeacher";
      this.chkTeacher.Size = new System.Drawing.Size(72, 16);
      this.chkTeacher.TabIndex = 99;
      this.chkTeacher.Text = "TEACHER";
      // 
      // chkUndead
      // 
      this.chkUndead.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkUndead.BackgroundImage")));
      this.chkUndead.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkUndead.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkUndead.Location = new System.Drawing.Point(552, 144);
      this.chkUndead.Name = "chkUndead";
      this.chkUndead.Size = new System.Drawing.Size(72, 16);
      this.chkUndead.TabIndex = 100;
      this.chkUndead.Text = "UNDEAD";
      // 
      // chkHerbalist
      // 
      this.chkHerbalist.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkHerbalist.BackgroundImage")));
      this.chkHerbalist.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkHerbalist.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkHerbalist.Location = new System.Drawing.Point(552, 96);
      this.chkHerbalist.Name = "chkHerbalist";
      this.chkHerbalist.Size = new System.Drawing.Size(88, 16);
      this.chkHerbalist.TabIndex = 101;
      this.chkHerbalist.Text = "HERBALIST";
      // 
      // chkSancd
      // 
      this.chkSancd.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkSancd.BackgroundImage")));
      this.chkSancd.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkSancd.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkSancd.Location = new System.Drawing.Point(552, 112);
      this.chkSancd.Name = "chkSancd";
      this.chkSancd.Size = new System.Drawing.Size(72, 16);
      this.chkSancd.TabIndex = 102;
      this.chkSancd.Text = "SANCD";
      // 
      // chkFlying
      // 
      this.chkFlying.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkFlying.BackgroundImage")));
      this.chkFlying.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkFlying.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkFlying.Location = new System.Drawing.Point(552, 128);
      this.chkFlying.Name = "chkFlying";
      this.chkFlying.Size = new System.Drawing.Size(72, 16);
      this.chkFlying.TabIndex = 103;
      this.chkFlying.Text = "FLYING";
      // 
      // chkAggro
      // 
      this.chkAggro.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkAggro.BackgroundImage")));
      this.chkAggro.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkAggro.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkAggro.Location = new System.Drawing.Point(552, 32);
      this.chkAggro.Name = "chkAggro";
      this.chkAggro.Size = new System.Drawing.Size(72, 16);
      this.chkAggro.TabIndex = 104;
      this.chkAggro.Text = "AGGRO";
      // 
      // frmMobEditor
      // 
      this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
      this.BackColor = System.Drawing.SystemColors.Menu;
      this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
      this.ClientSize = new System.Drawing.Size(870, 572);
      this.Controls.Add(this.chkAggro);
      this.Controls.Add(this.chkFlying);
      this.Controls.Add(this.chkSancd);
      this.Controls.Add(this.chkHerbalist);
      this.Controls.Add(this.chkUndead);
      this.Controls.Add(this.chkTeacher);
      this.Controls.Add(this.chkClergy);
      this.Controls.Add(this.txtBDir);
      this.Controls.Add(this.txtBType);
      this.Controls.Add(this.txtActionFreq);
      this.Controls.Add(this.txtAction);
      this.Controls.Add(this.txtSpellFreq);
      this.Controls.Add(this.txtSpellName);
      this.Controls.Add(this.txtSkillFreq);
      this.Controls.Add(this.txtSkillName);
      this.Controls.Add(this.txtShopName);
      this.Controls.Add(this.txtMR);
      this.Controls.Add(this.txtAC);
      this.Controls.Add(this.txtDAM);
      this.Controls.Add(this.txtHIT);
      this.Controls.Add(this.txtCON);
      this.Controls.Add(this.txtDEX);
      this.Controls.Add(this.txtWIS);
      this.Controls.Add(this.txtINT);
      this.Controls.Add(this.txtSTR);
      this.Controls.Add(this.txtSpeed);
      this.Controls.Add(this.txtAllign);
      this.Controls.Add(this.txtGold);
      this.Controls.Add(this.txtExp);
      this.Controls.Add(this.txtMana);
      this.Controls.Add(this.txtHitPoints);
      this.Controls.Add(this.txtLevel);
      this.Controls.Add(this.txtLookDesc);
      this.Controls.Add(this.txtRoomDesc);
      this.Controls.Add(this.txtKeywords);
      this.Controls.Add(this.txtName);
      this.Controls.Add(this.txtID);
      this.Controls.Add(this.chkBlocker);
      this.Controls.Add(this.lblBDir);
      this.Controls.Add(this.lblBType);
      this.Controls.Add(this.label37);
      this.Controls.Add(this.label36);
      this.Controls.Add(this.lstSpells);
      this.Controls.Add(this.label34);
      this.Controls.Add(this.label35);
      this.Controls.Add(this.label33);
      this.Controls.Add(this.label32);
      this.Controls.Add(this.lstSkills);
      this.Controls.Add(this.cmdRemoveSpell);
      this.Controls.Add(this.cmdAddSpell);
      this.Controls.Add(this.label31);
      this.Controls.Add(this.label30);
      this.Controls.Add(this.label29);
      this.Controls.Add(this.label28);
      this.Controls.Add(this.cmdRemoveSellItem);
      this.Controls.Add(this.cmdAddSellItem);
      this.Controls.Add(this.cmdRemoveSkill);
      this.Controls.Add(this.cmdAddSkill);
      this.Controls.Add(this.cmdRemoveWearItem);
      this.Controls.Add(this.cmdAddWearItem);
      this.Controls.Add(this.cmdRemoveLoadItem);
      this.Controls.Add(this.cmdAddLoadItem);
      this.Controls.Add(this.lstSellItems);
      this.Controls.Add(this.lstWearItems);
      this.Controls.Add(this.lstLoadItems);
      this.Controls.Add(this.label27);
      this.Controls.Add(this.label26);
      this.Controls.Add(this.label25);
      this.Controls.Add(this.label24);
      this.Controls.Add(this.label23);
      this.Controls.Add(this.cmdRemoveAction);
      this.Controls.Add(this.cmdAddAction);
      this.Controls.Add(this.lstActions);
      this.Controls.Add(this.label22);
      this.Controls.Add(this.label21);
      this.Controls.Add(this.label20);
      this.Controls.Add(this.label19);
      this.Controls.Add(this.label18);
      this.Controls.Add(this.label17);
      this.Controls.Add(this.label16);
      this.Controls.Add(this.label15);
      this.Controls.Add(this.label14);
      this.Controls.Add(this.label13);
      this.Controls.Add(this.label12);
      this.Controls.Add(this.label11);
      this.Controls.Add(this.label10);
      this.Controls.Add(this.label9);
      this.Controls.Add(this.label8);
      this.Controls.Add(this.label7);
      this.Controls.Add(this.label6);
      this.Controls.Add(this.label5);
      this.Controls.Add(this.label4);
      this.Controls.Add(this.label3);
      this.Controls.Add(this.label2);
      this.Controls.Add(this.label1);
      this.Controls.Add(this.cmdCancel);
      this.Controls.Add(this.cmdSave);
      this.Controls.Add(this.chkSpellmaster);
      this.Controls.Add(this.chkSkillmaster);
      this.Controls.Add(this.chkGuard);
      this.Controls.Add(this.chkMobile);
      this.Controls.Add(this.chkBard);
      this.Controls.Add(this.chkBanker);
      this.Controls.Add(this.chkMerchant);
      this.Controls.Add(this.chkNPC);
      this.Controls.Add(this.rdoFemale);
      this.Controls.Add(this.rdoMale);
      this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
      this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
      this.MaximizeBox = false;
      this.MinimizeBox = false;
      this.Name = "frmMobEditor";
      this.ShowInTaskbar = false;
      this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
      this.Text = "Mob Editor";
      this.ResumeLayout(false);

    }
    #endregion

    private void Changed_Click(object sender, System.EventArgs e)

    {
      if (watch_change)
        changed = true;
    }

    private bool check_numerical(Control data, string output)

    {
      if (!check_text(data, output)) return false;

      for (int i=0; i<data.Text.Length; i++)
        if ((data.Text[i] < '0') || (data.Text[i] > '9'))

        {
          MessageBox.Show(output + " must contain only numerical values.");
          return false;
        }

      return true;
    }

    private bool check_text(Control data, string output)

    {
      if (data.Text == "")

      {
        MessageBox.Show(output + " cannot be empty.");
        return false;
      }

      return true;
    }

    private bool validate()

    {
      if (!check_text(txtName, "Name")) return false;
      if (!check_text(txtKeywords, "Keywords")) return false;
      if (!check_text(txtRoomDesc, "Room Desc")) return false;
      if (!check_text(txtLookDesc, "Look Desc")) return false;
      if (!check_numerical(txtID, "Mob ID")) return false;
      if (!check_numerical(txtLevel, "Level")) return false;
      if (!check_numerical(txtHitPoints, "HP")) return false;
      if (!check_numerical(txtMana, "Mana")) return false;
      if (!check_numerical(txtExp, "Exp")) return false;
      if (!check_numerical(txtGold, "Gold")) return false;
      if (!check_numerical(txtAllign, "Align")) return false;
      if (!check_numerical(txtSpeed, "Speed")) return false;
      if (!check_numerical(txtSTR, "Strength")) return false;
      if (!check_numerical(txtINT, "Intelligence")) return false;
      if (!check_numerical(txtWIS, "Wisdom")) return false;
      if (!check_numerical(txtDEX, "Dexerity")) return false;
      if (!check_numerical(txtCON, "Constitution")) return false;
      if (!check_numerical(txtHIT, "Hit roll")) return false;
      if (!check_numerical(txtDAM, "Dam roll")) return false;
      if (!check_numerical(txtAC, "Armor class")) return false;
      if (!check_numerical(txtMR, "Magic resistance")) return false;

      int n = Int32.Parse(frmMain.get_zone_id());
      int m = (mob_list.get_size() / 100);
      int min_id = n * 100;
      int max_id = min_id + 99 + (100*m);
      int id_num = Int32.Parse(txtID.Text);

      if ((id_num < min_id) || (id_num > max_id))

      {
        if (MessageBox.Show ("Mob ID " + id_num.ToString() + " does not fall within this Zone's Range. (" +
        min_id.ToString() + "-" + max_id.ToString() + ").  Do you want to save anyway?", "Mob ID out of bounds.",
        MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
          return true;

        return false;
      }

      return true;
    }

    private void cmdSave_Click(object sender, System.EventArgs e)

    {
      if (changed)

      {
        if (!validate()) return;

        if (original_id == txtID.Text)
          if (mob_list.find_mob(txtID.Text) == null)

          {
            save_mob();
            mob_list.add_mob(active_mob);
          }

          else

          {
            mob_list.remove_mob(active_mob);
            save_mob();
            mob_list.add_mob(active_mob);
          }

        else

        {
          if (mob_list.find_mob(txtID.Text) == null)

          {
            if (original_id != "")
              if (mob_list.find_mob(original_id) != null)
                mob_list.remove_mob(mob_list.find_mob(original_id));

            save_mob();
            mob_list.add_mob(active_mob);
          }

          else
            if (MessageBox.Show ("Mob ID changed. This will overwrite the existing mob with the id of " + txtID.Text + ". Continue?", "Mob Replacement Warning",
            MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)

          {
            if (mob_list.find_mob(original_id) != null)
              mob_list.remove_mob(mob_list.find_mob(original_id));

            if (mob_list.find_mob(txtID.Text) != null)
              mob_list.remove_mob(mob_list.find_mob(txtID.Text));

            save_mob();
            mob_list.add_mob(active_mob);
          }
        }

        if (saved)

        {
          active = false;
          this.Hide();
        }
      }

      else

      {
        active = false;
        this.Hide();
      }
    }

    private void cmdCancel_Click(object sender, System.EventArgs e)

    {
      if (changed)

      {
        if (MessageBox.Show ("Do you want to save your changes?", "Mob Cancel",
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

    private void cmdAddLoadItem_Click(object sender, System.EventArgs e)

    {
      item_picker.enable_freq();
      item_picker.ShowDialog();

      if (item_picker.get_saved())

      {
        litems.add_item(item_picker.get_selected());
        load_items_freq.Add(item_picker.get_freq());
      }

      item_picker.disable_freq();
      update_litems();
    }

    private void cmdRemoveLoadItem_Click(object sender, System.EventArgs e)

    {
      if (lstLoadItems.SelectedItem != null)

      {
        load_items_freq.RemoveAt(lstLoadItems.SelectedIndex);
        litems.remove_item(lstLoadItems.SelectedIndex);
        update_litems();
      }
    }

    private void chkMerchant_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
        if (chkMerchant.Checked)

        {
          txtShopName.ReadOnly = false;
          txtShopName.Text = "";
        }

        else

        {
          txtShopName.ReadOnly = true;
          txtShopName.Text = "";
        }
    }

    private void chkBlocker_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
        if (chkBlocker.Checked)

        {
          txtBType.ReadOnly = false;
          txtBType.Text = "";
          txtBDir.ReadOnly = false;
          txtBDir.Text = "";
        }

        else

        {
          txtBType.ReadOnly = true;
          txtBType.Text = "";
          txtBDir.ReadOnly = true;
          txtBDir.Text = "";
        }
    }

    protected override void Dispose( bool disposing )

    {
      if( disposing )
        if(components != null)
          components.Dispose();

      base.Dispose( disposing );
    }

    private void cmdAddWearItem_Click(object sender, System.EventArgs e)

    {
      item_picker.enable_freq();
      item_picker.ShowDialog();

      if (item_picker.get_saved())

      {
        witems.add_item(item_picker.get_selected());
        wear_items_freq.Add(item_picker.get_freq());
      }

      item_picker.disable_freq();
      update_witems();
    }

    private void cmdRemoveWearItem_Click(object sender, System.EventArgs e)

    {
      if (lstWearItems.SelectedItem != null)

      {
        wear_items_freq.RemoveAt(lstWearItems.SelectedIndex);
        witems.remove_item(lstWearItems.SelectedIndex);
        update_witems();
      }
    }

    private void cmdAddSellItem_Click(object sender, System.EventArgs e)

    {
      item_picker.disable_freq();
      item_picker.ShowDialog();

      if (item_picker.get_saved())
        sitems.add_item(item_picker.get_selected());

      update_sitems();
    }

    private void cmdRemoveSellItem_Click(object sender, System.EventArgs e)

    {
      if (lstSellItems.SelectedItem != null)

      {
        sitems.remove_item(lstSellItems.SelectedIndex);
        update_sitems();
      }
    }

    private bool check_frequency(string freq)

    {
      int i = -1;

      try

      {
        i = Int32.Parse(freq);

        if (i < 0 || i > 60000)
          throw new System.Exception();
      }

      catch (System.FormatException)

      {
        MessageBox.Show("Invalid frequency setting.");
        return false;
      }

      catch (System.Exception)

      {
        MessageBox.Show("Frequency out of bounds.  Must be between 0-60,000");
        return false;
      }

      return true;
    }

    private void cmdAddSkill_Click(object sender, System.EventArgs e)

    {
      if ((txtSkillName.Text != "") && (txtSkillFreq.Text != ""))

      {
        if (!check_frequency(txtSkillFreq.Text)) return;

        string temp = Utility.clear_whitespace(txtSkillFreq.Text + " " + txtSkillName.Text);
        skills.Add(temp);
        update_skillslist();
      }
    }

    private void cmdRemoveSkill_Click(object sender, System.EventArgs e)

    {
      string temp = txtSkillFreq.Text + " " + txtSkillName.Text;
      skills.Remove(temp);
      update_skillslist();
    }

    private void lstSkills_SelectedIndexChanged(object sender, System.EventArgs e)

    {
      string temp = (string)lstSkills.SelectedItem;
      string frequency = first(temp);
      string name = last(temp);

      txtSkillName.Text = name;
      txtSkillFreq.Text = frequency;
    }

    private void cmdAddSpell_Click(object sender, System.EventArgs e)

    {
      if ((txtSpellName.Text != "") && (txtSpellFreq.Text != ""))

      {
        if (!check_frequency(txtSpellFreq.Text)) return;

        string temp = Utility.clear_whitespace(txtSpellFreq.Text + " " + txtSpellName.Text);
        spells.Add(temp);
        update_spellslist();
      }
    }

    private void cmdRemoveSpell_Click(object sender, System.EventArgs e)

    {
      string temp = txtSpellFreq.Text + " " + txtSpellName.Text;
      spells.Remove(temp);
      update_spellslist();
    }

    private void lstSpells_SelectedIndexChanged(object sender, System.EventArgs e)

    {
      string temp = (string)lstSpells.SelectedItem;
      string frequency = first(temp);
      string name = last(temp);

      txtSpellName.Text = name;
      txtSpellFreq.Text = frequency;
    }

    private void cmdAddAction_Click(object sender, System.EventArgs e)

    {
      if ((txtAction.Text != "") && (txtActionFreq.Text != ""))

      {
        if (!check_frequency(txtActionFreq.Text)) return;

        string temp = Utility.clear_whitespace(txtActionFreq.Text + " " + txtAction.Text);
        actions.Add(temp);
        update_actionslist();
      }
    }

    private void cmdRemoveAction_Click(object sender, System.EventArgs e)

    {
      string temp = txtActionFreq.Text + " " + txtAction.Text;
      actions.Remove(temp);
      update_actionslist();
    }

    private void lstActions_SelectedIndexChanged(object sender, System.EventArgs e)

    {
      string temp = (string)lstActions.SelectedItem;
      string frequency = first(temp);
      string name = last(temp);

      txtAction.Text = name;
      txtActionFreq.Text = frequency;
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
  }
}
