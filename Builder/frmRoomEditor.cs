using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace Tempest_Builder

{
  public class frmRoomEditor : System.Windows.Forms.Form

  {

    private room          active_room;
    private roomlist      room_list;
    private frmLookEditor look_editor;
    private frmItemPicker item_picker;
    private frmMobPicker  mob_picker;
    private bool          active, changed;
    private ArrayList     keys,
                          descs,
                          exitkeys,
                          exitdescs,
                          actions;

    private moblist mlist;
    private itemlist ilist;
    private string original_id;
    private bool watch_change;
    private string exact_holder;

    #region GUI variables
    private System.Windows.Forms.Label label1;
    private System.Windows.Forms.Label label2;
    private System.Windows.Forms.Label label3;
    private System.Windows.Forms.TextBox txtID;
    private System.Windows.Forms.TextBox txtName;
    private System.Windows.Forms.TextBox txtDesc;
    private System.Windows.Forms.ListBox lstMob;
    private System.Windows.Forms.Button cmdAddMob;
    private System.Windows.Forms.Button cmdRemoveMob;
    private System.Windows.Forms.ListBox lstItem;
    private System.Windows.Forms.Button cmdAddItem;
    private System.Windows.Forms.Button cmdRemoveItem;
    private System.Windows.Forms.ListBox lstLook;
    private System.Windows.Forms.Button cmdSave;
    private System.Windows.Forms.Button cmdCancel;
    private System.Windows.Forms.Button cmdAddLook;
    private System.Windows.Forms.Button cmdEditLook;
    private System.Windows.Forms.Button cmdRemoveLook;
    private System.Windows.Forms.TextBox txtNorth;
    private System.Windows.Forms.TextBox txtSouth;
    private System.Windows.Forms.TextBox txtEast;
    private System.Windows.Forms.TextBox txtWest;
    private System.Windows.Forms.TextBox txtUp;
    private System.Windows.Forms.TextBox txtDown;
    private System.Windows.Forms.CheckBox chkNorth;
    private System.Windows.Forms.CheckBox chkSouth;
    private System.Windows.Forms.CheckBox chkEast;
    private System.Windows.Forms.CheckBox chkWest;
    private System.Windows.Forms.CheckBox chkUp;
    private System.Windows.Forms.CheckBox chkDown;
    private System.Windows.Forms.Label label4;
    private System.Windows.Forms.Label label5;
    private System.Windows.Forms.Label label6;
    private System.Windows.Forms.Button cmdNorthLook;
    private System.Windows.Forms.Button cmdSouthLook;
    private System.Windows.Forms.Button cmdEastLook;
    private System.Windows.Forms.Button cmdWestLook;
    private System.Windows.Forms.Button cmdUpLook;
    private System.Windows.Forms.Button cmdDownLook;
    private System.Windows.Forms.CheckBox chkDONATION;
    private System.Windows.Forms.CheckBox chkNOMOB;
    private System.Windows.Forms.CheckBox chkNODROP;
    private System.Windows.Forms.CheckBox chkPRIVATE;
    private System.Windows.Forms.CheckBox chkSILENT;
    private System.Windows.Forms.CheckBox chkNOQUIT;
    private System.Windows.Forms.CheckBox chkNOSUMMON;
    private System.Windows.Forms.CheckBox chkINDOORS;
    private System.Windows.Forms.CheckBox chkNOMAGIC;
    private System.Windows.Forms.CheckBox chkLAWFULL;
    private System.Windows.Forms.Label label7;
    private System.Windows.Forms.Label label8;
    private System.Windows.Forms.Label label9;
    private System.Windows.Forms.ListBox lstActions;
    private System.Windows.Forms.Button cmdAddAction;
    private System.Windows.Forms.Button cmdRemoveAction;
    private System.Windows.Forms.TextBox txtActionFreq;
    private System.Windows.Forms.TextBox txtAction;
    private System.Windows.Forms.Label label37;
    private System.Windows.Forms.Label label36;
    private System.Windows.Forms.Button cmdLinkNorth;
    private System.Windows.Forms.Button cmdLinkSouth;
    private System.Windows.Forms.Button cmdLinkEast;
    private System.Windows.Forms.Button cmdLinkWest;
    private System.Windows.Forms.Button cmdLinkUp;
    private System.Windows.Forms.Button cmdLinkDown;
    private System.Windows.Forms.Button cmdReverseLink;
    private System.Windows.Forms.Button cmdLinkAll;
    private System.Windows.Forms.CheckBox chkDark;
    private System.Windows.Forms.CheckBox chkChurch;
    private System.Windows.Forms.CheckBox chkTunnel;
    private System.Windows.Forms.CheckBox chkDamage;
    private System.Windows.Forms.CheckBox chkDeathTrap;
    private System.Windows.Forms.CheckBox chkUnderwater;
    private System.Windows.Forms.CheckBox chkGodRoom;
    private System.Windows.Forms.CheckBox chkShroud;
    private System.Windows.Forms.CheckBox chkNoScout;
    private System.Windows.Forms.CheckBox chkNoDispel;
    private System.Windows.Forms.CheckBox chkAnimate;
    private System.Windows.Forms.CheckBox chkNoTrack;
    private System.Windows.Forms.CheckBox chkNoTeleport;
    private System.Windows.Forms.CheckBox chkNoFlee;
    private System.Windows.Forms.CheckBox chkNoSpell;
    private System.Windows.Forms.CheckBox chkPKOK;
    private System.Windows.Forms.CheckBox chkRegenHP;
    private System.Windows.Forms.CheckBox chkRegenMN;
    private System.Windows.Forms.Label label10;
    private System.Windows.Forms.ComboBox cboTerrain;
    private System.Windows.Forms.Button cmdDoubleLink;
    private System.Windows.Forms.CheckBox chkExact;
    private System.Windows.Forms.Button cmdExact;
    private System.ComponentModel.Container components = null;
    #endregion

    public frmRoomEditor(roomlist rlist, frmItemPicker ipicker, frmMobPicker mpicker)

    {
      InitializeComponent();
      room_list = rlist;
      item_picker = ipicker;
      mob_picker = mpicker;
      look_editor = new frmLookEditor();

      for (int i=0; i<this.Controls.Count; i++)
        if ((Controls[i] != cmdCancel) && (Controls[i] != cmdSave))
          Controls[i].GotFocus += new EventHandler(this.frmRoomEditor_Click);
    }

    private void InitializeComponent()
    {
      System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(frmRoomEditor));
      this.label1 = new System.Windows.Forms.Label();
      this.label2 = new System.Windows.Forms.Label();
      this.label3 = new System.Windows.Forms.Label();
      this.txtID = new System.Windows.Forms.TextBox();
      this.txtName = new System.Windows.Forms.TextBox();
      this.txtDesc = new System.Windows.Forms.TextBox();
      this.lstMob = new System.Windows.Forms.ListBox();
      this.cmdAddMob = new System.Windows.Forms.Button();
      this.cmdRemoveMob = new System.Windows.Forms.Button();
      this.lstItem = new System.Windows.Forms.ListBox();
      this.cmdAddItem = new System.Windows.Forms.Button();
      this.cmdRemoveItem = new System.Windows.Forms.Button();
      this.lstLook = new System.Windows.Forms.ListBox();
      this.cmdAddLook = new System.Windows.Forms.Button();
      this.cmdEditLook = new System.Windows.Forms.Button();
      this.cmdRemoveLook = new System.Windows.Forms.Button();
      this.cmdSave = new System.Windows.Forms.Button();
      this.cmdCancel = new System.Windows.Forms.Button();
      this.txtNorth = new System.Windows.Forms.TextBox();
      this.txtSouth = new System.Windows.Forms.TextBox();
      this.txtEast = new System.Windows.Forms.TextBox();
      this.txtWest = new System.Windows.Forms.TextBox();
      this.txtUp = new System.Windows.Forms.TextBox();
      this.txtDown = new System.Windows.Forms.TextBox();
      this.chkNorth = new System.Windows.Forms.CheckBox();
      this.chkSouth = new System.Windows.Forms.CheckBox();
      this.chkEast = new System.Windows.Forms.CheckBox();
      this.chkWest = new System.Windows.Forms.CheckBox();
      this.chkUp = new System.Windows.Forms.CheckBox();
      this.chkDown = new System.Windows.Forms.CheckBox();
      this.label4 = new System.Windows.Forms.Label();
      this.label5 = new System.Windows.Forms.Label();
      this.label6 = new System.Windows.Forms.Label();
      this.cmdNorthLook = new System.Windows.Forms.Button();
      this.cmdSouthLook = new System.Windows.Forms.Button();
      this.cmdEastLook = new System.Windows.Forms.Button();
      this.cmdWestLook = new System.Windows.Forms.Button();
      this.cmdUpLook = new System.Windows.Forms.Button();
      this.cmdDownLook = new System.Windows.Forms.Button();
      this.chkDONATION = new System.Windows.Forms.CheckBox();
      this.chkNOMOB = new System.Windows.Forms.CheckBox();
      this.chkNODROP = new System.Windows.Forms.CheckBox();
      this.chkPRIVATE = new System.Windows.Forms.CheckBox();
      this.chkSILENT = new System.Windows.Forms.CheckBox();
      this.chkNOQUIT = new System.Windows.Forms.CheckBox();
      this.chkNOSUMMON = new System.Windows.Forms.CheckBox();
      this.chkINDOORS = new System.Windows.Forms.CheckBox();
      this.chkRegenHP = new System.Windows.Forms.CheckBox();
      this.chkNOMAGIC = new System.Windows.Forms.CheckBox();
      this.chkLAWFULL = new System.Windows.Forms.CheckBox();
      this.chkPKOK = new System.Windows.Forms.CheckBox();
      this.label7 = new System.Windows.Forms.Label();
      this.label8 = new System.Windows.Forms.Label();
      this.label9 = new System.Windows.Forms.Label();
      this.lstActions = new System.Windows.Forms.ListBox();
      this.cmdAddAction = new System.Windows.Forms.Button();
      this.cmdRemoveAction = new System.Windows.Forms.Button();
      this.txtActionFreq = new System.Windows.Forms.TextBox();
      this.txtAction = new System.Windows.Forms.TextBox();
      this.label37 = new System.Windows.Forms.Label();
      this.label36 = new System.Windows.Forms.Label();
      this.cmdLinkNorth = new System.Windows.Forms.Button();
      this.cmdLinkSouth = new System.Windows.Forms.Button();
      this.cmdLinkEast = new System.Windows.Forms.Button();
      this.cmdLinkWest = new System.Windows.Forms.Button();
      this.cmdLinkUp = new System.Windows.Forms.Button();
      this.cmdLinkDown = new System.Windows.Forms.Button();
      this.cmdReverseLink = new System.Windows.Forms.Button();
      this.cmdLinkAll = new System.Windows.Forms.Button();
      this.chkDark = new System.Windows.Forms.CheckBox();
      this.chkChurch = new System.Windows.Forms.CheckBox();
      this.chkTunnel = new System.Windows.Forms.CheckBox();
      this.chkDamage = new System.Windows.Forms.CheckBox();
      this.chkDeathTrap = new System.Windows.Forms.CheckBox();
      this.chkUnderwater = new System.Windows.Forms.CheckBox();
      this.chkGodRoom = new System.Windows.Forms.CheckBox();
      this.chkShroud = new System.Windows.Forms.CheckBox();
      this.chkNoTrack = new System.Windows.Forms.CheckBox();
      this.chkNoScout = new System.Windows.Forms.CheckBox();
      this.chkNoDispel = new System.Windows.Forms.CheckBox();
      this.chkNoTeleport = new System.Windows.Forms.CheckBox();
      this.chkNoFlee = new System.Windows.Forms.CheckBox();
      this.chkAnimate = new System.Windows.Forms.CheckBox();
      this.chkNoSpell = new System.Windows.Forms.CheckBox();
      this.chkRegenMN = new System.Windows.Forms.CheckBox();
      this.cmdDoubleLink = new System.Windows.Forms.Button();
      this.label10 = new System.Windows.Forms.Label();
      this.cboTerrain = new System.Windows.Forms.ComboBox();
      this.chkExact = new System.Windows.Forms.CheckBox();
      this.cmdExact = new System.Windows.Forms.Button();
      this.SuspendLayout();
      // 
      // label1
      // 
      this.label1.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label1.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label1.ForeColor = System.Drawing.SystemColors.ControlText;
      this.label1.Location = new System.Drawing.Point(8, 8);
      this.label1.Name = "label1";
      this.label1.Size = new System.Drawing.Size(40, 16);
      this.label1.TabIndex = 0;
      this.label1.Text = "ID:";
      this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label2
      // 
      this.label2.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label2.ForeColor = System.Drawing.SystemColors.ControlText;
      this.label2.Location = new System.Drawing.Point(8, 32);
      this.label2.Name = "label2";
      this.label2.Size = new System.Drawing.Size(40, 16);
      this.label2.TabIndex = 1;
      this.label2.Text = "Name:";
      this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label3
      // 
      this.label3.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label3.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label3.ForeColor = System.Drawing.SystemColors.ControlText;
      this.label3.Location = new System.Drawing.Point(8, 56);
      this.label3.Name = "label3";
      this.label3.Size = new System.Drawing.Size(40, 16);
      this.label3.TabIndex = 2;
      this.label3.Text = "Desc:";
      this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtID
      // 
      this.txtID.BackColor = System.Drawing.SystemColors.Window;
      this.txtID.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtID.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtID.ForeColor = System.Drawing.SystemColors.ControlText;
      this.txtID.Location = new System.Drawing.Point(56, 8);
      this.txtID.Name = "txtID";
      this.txtID.Size = new System.Drawing.Size(88, 20);
      this.txtID.TabIndex = 3;
      this.txtID.Text = "";
      // 
      // txtName
      // 
      this.txtName.BackColor = System.Drawing.SystemColors.Menu;
      this.txtName.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtName.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtName.ForeColor = System.Drawing.SystemColors.ControlText;
      this.txtName.Location = new System.Drawing.Point(56, 32);
      this.txtName.Name = "txtName";
      this.txtName.Size = new System.Drawing.Size(288, 20);
      this.txtName.TabIndex = 4;
      this.txtName.Text = "";
      // 
      // txtDesc
      // 
      this.txtDesc.BackColor = System.Drawing.SystemColors.Window;
      this.txtDesc.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtDesc.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtDesc.ForeColor = System.Drawing.SystemColors.WindowText;
      this.txtDesc.Location = new System.Drawing.Point(56, 56);
      this.txtDesc.Multiline = true;
      this.txtDesc.Name = "txtDesc";
      this.txtDesc.Size = new System.Drawing.Size(288, 288);
      this.txtDesc.TabIndex = 5;
      this.txtDesc.Text = "";
      // 
      // lstMob
      // 
      this.lstMob.BackColor = System.Drawing.SystemColors.Menu;
      this.lstMob.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstMob.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.lstMob.ForeColor = System.Drawing.SystemColors.ControlText;
      this.lstMob.ItemHeight = 14;
      this.lstMob.Location = new System.Drawing.Point(568, 232);
      this.lstMob.Name = "lstMob";
      this.lstMob.Size = new System.Drawing.Size(211, 86);
      this.lstMob.TabIndex = 6;
      // 
      // cmdAddMob
      // 
      this.cmdAddMob.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddMob.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddMob.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdAddMob.Location = new System.Drawing.Point(568, 320);
      this.cmdAddMob.Name = "cmdAddMob";
      this.cmdAddMob.TabIndex = 7;
      this.cmdAddMob.Text = "Add";
      this.cmdAddMob.Click += new System.EventHandler(this.cmdAddMob_Click);
      // 
      // cmdRemoveMob
      // 
      this.cmdRemoveMob.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveMob.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveMob.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdRemoveMob.Location = new System.Drawing.Point(704, 320);
      this.cmdRemoveMob.Name = "cmdRemoveMob";
      this.cmdRemoveMob.TabIndex = 8;
      this.cmdRemoveMob.Text = "Remove";
      this.cmdRemoveMob.Click += new System.EventHandler(this.cmdRemoveMob_Click);
      // 
      // lstItem
      // 
      this.lstItem.BackColor = System.Drawing.SystemColors.Menu;
      this.lstItem.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstItem.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.lstItem.ForeColor = System.Drawing.SystemColors.ControlText;
      this.lstItem.ItemHeight = 14;
      this.lstItem.Location = new System.Drawing.Point(352, 232);
      this.lstItem.Name = "lstItem";
      this.lstItem.Size = new System.Drawing.Size(211, 86);
      this.lstItem.TabIndex = 9;
      // 
      // cmdAddItem
      // 
      this.cmdAddItem.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddItem.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddItem.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdAddItem.Location = new System.Drawing.Point(352, 320);
      this.cmdAddItem.Name = "cmdAddItem";
      this.cmdAddItem.TabIndex = 10;
      this.cmdAddItem.Text = "Add";
      this.cmdAddItem.Click += new System.EventHandler(this.cmdAddItem_Click);
      // 
      // cmdRemoveItem
      // 
      this.cmdRemoveItem.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveItem.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveItem.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdRemoveItem.Location = new System.Drawing.Point(488, 320);
      this.cmdRemoveItem.Name = "cmdRemoveItem";
      this.cmdRemoveItem.TabIndex = 11;
      this.cmdRemoveItem.Text = "Remove";
      this.cmdRemoveItem.Click += new System.EventHandler(this.cmdRemoveItem_Click);
      // 
      // lstLook
      // 
      this.lstLook.BackColor = System.Drawing.SystemColors.Menu;
      this.lstLook.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstLook.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.lstLook.ForeColor = System.Drawing.SystemColors.ControlText;
      this.lstLook.ItemHeight = 14;
      this.lstLook.Location = new System.Drawing.Point(568, 32);
      this.lstLook.Name = "lstLook";
      this.lstLook.Size = new System.Drawing.Size(211, 86);
      this.lstLook.Sorted = true;
      this.lstLook.TabIndex = 12;
      // 
      // cmdAddLook
      // 
      this.cmdAddLook.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddLook.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddLook.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdAddLook.Location = new System.Drawing.Point(568, 120);
      this.cmdAddLook.Name = "cmdAddLook";
      this.cmdAddLook.TabIndex = 13;
      this.cmdAddLook.Text = "Add";
      this.cmdAddLook.Click += new System.EventHandler(this.cmdAddLook_Click);
      // 
      // cmdEditLook
      // 
      this.cmdEditLook.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdEditLook.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdEditLook.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdEditLook.Location = new System.Drawing.Point(704, 120);
      this.cmdEditLook.Name = "cmdEditLook";
      this.cmdEditLook.TabIndex = 14;
      this.cmdEditLook.Text = "Edit";
      this.cmdEditLook.Click += new System.EventHandler(this.cmdEditLook_Click);
      // 
      // cmdRemoveLook
      // 
      this.cmdRemoveLook.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveLook.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveLook.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdRemoveLook.Location = new System.Drawing.Point(568, 144);
      this.cmdRemoveLook.Name = "cmdRemoveLook";
      this.cmdRemoveLook.TabIndex = 15;
      this.cmdRemoveLook.Text = "Remove";
      this.cmdRemoveLook.Click += new System.EventHandler(this.cmdRemoveLook_Click);
      // 
      // cmdSave
      // 
      this.cmdSave.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdSave.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdSave.Location = new System.Drawing.Point(304, 528);
      this.cmdSave.Name = "cmdSave";
      this.cmdSave.TabIndex = 16;
      this.cmdSave.Text = "Save";
      this.cmdSave.Click += new System.EventHandler(this.cmdSave_Click);
      // 
      // cmdCancel
      // 
      this.cmdCancel.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdCancel.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdCancel.Location = new System.Drawing.Point(408, 528);
      this.cmdCancel.Name = "cmdCancel";
      this.cmdCancel.TabIndex = 17;
      this.cmdCancel.Text = "Cancel";
      this.cmdCancel.Click += new System.EventHandler(this.cmdCancel_Click);
      // 
      // txtNorth
      // 
      this.txtNorth.BackColor = System.Drawing.SystemColors.Menu;
      this.txtNorth.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtNorth.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtNorth.ForeColor = System.Drawing.SystemColors.ControlText;
      this.txtNorth.Location = new System.Drawing.Point(432, 376);
      this.txtNorth.Name = "txtNorth";
      this.txtNorth.ReadOnly = true;
      this.txtNorth.Size = new System.Drawing.Size(64, 20);
      this.txtNorth.TabIndex = 24;
      this.txtNorth.Text = "";
      // 
      // txtSouth
      // 
      this.txtSouth.BackColor = System.Drawing.SystemColors.Menu;
      this.txtSouth.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtSouth.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtSouth.ForeColor = System.Drawing.SystemColors.ControlText;
      this.txtSouth.Location = new System.Drawing.Point(432, 400);
      this.txtSouth.Name = "txtSouth";
      this.txtSouth.ReadOnly = true;
      this.txtSouth.Size = new System.Drawing.Size(64, 20);
      this.txtSouth.TabIndex = 25;
      this.txtSouth.Text = "";
      // 
      // txtEast
      // 
      this.txtEast.BackColor = System.Drawing.SystemColors.Menu;
      this.txtEast.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtEast.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtEast.ForeColor = System.Drawing.SystemColors.ControlText;
      this.txtEast.Location = new System.Drawing.Point(432, 424);
      this.txtEast.Name = "txtEast";
      this.txtEast.ReadOnly = true;
      this.txtEast.Size = new System.Drawing.Size(64, 20);
      this.txtEast.TabIndex = 26;
      this.txtEast.Text = "";
      // 
      // txtWest
      // 
      this.txtWest.BackColor = System.Drawing.SystemColors.Menu;
      this.txtWest.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtWest.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtWest.ForeColor = System.Drawing.SystemColors.ControlText;
      this.txtWest.Location = new System.Drawing.Point(432, 448);
      this.txtWest.Name = "txtWest";
      this.txtWest.ReadOnly = true;
      this.txtWest.Size = new System.Drawing.Size(64, 20);
      this.txtWest.TabIndex = 27;
      this.txtWest.Text = "";
      // 
      // txtUp
      // 
      this.txtUp.BackColor = System.Drawing.SystemColors.Menu;
      this.txtUp.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtUp.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtUp.ForeColor = System.Drawing.SystemColors.ControlText;
      this.txtUp.Location = new System.Drawing.Point(432, 472);
      this.txtUp.Name = "txtUp";
      this.txtUp.ReadOnly = true;
      this.txtUp.Size = new System.Drawing.Size(64, 20);
      this.txtUp.TabIndex = 28;
      this.txtUp.Text = "";
      // 
      // txtDown
      // 
      this.txtDown.BackColor = System.Drawing.SystemColors.Menu;
      this.txtDown.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtDown.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtDown.ForeColor = System.Drawing.SystemColors.ControlText;
      this.txtDown.Location = new System.Drawing.Point(432, 496);
      this.txtDown.Name = "txtDown";
      this.txtDown.ReadOnly = true;
      this.txtDown.Size = new System.Drawing.Size(64, 20);
      this.txtDown.TabIndex = 29;
      this.txtDown.Text = "";
      // 
      // chkNorth
      // 
      this.chkNorth.BackColor = System.Drawing.SystemColors.Menu;
      this.chkNorth.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNorth.BackgroundImage")));
      this.chkNorth.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNorth.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNorth.ForeColor = System.Drawing.SystemColors.ControlText;
      this.chkNorth.Location = new System.Drawing.Point(368, 376);
      this.chkNorth.Name = "chkNorth";
      this.chkNorth.Size = new System.Drawing.Size(56, 16);
      this.chkNorth.TabIndex = 36;
      this.chkNorth.Text = "North";
      this.chkNorth.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
      this.chkNorth.CheckedChanged += new System.EventHandler(this.chkNorth_CheckedChanged);
      // 
      // chkSouth
      // 
      this.chkSouth.BackColor = System.Drawing.SystemColors.Menu;
      this.chkSouth.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkSouth.BackgroundImage")));
      this.chkSouth.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkSouth.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkSouth.ForeColor = System.Drawing.SystemColors.ControlText;
      this.chkSouth.Location = new System.Drawing.Point(368, 400);
      this.chkSouth.Name = "chkSouth";
      this.chkSouth.Size = new System.Drawing.Size(56, 16);
      this.chkSouth.TabIndex = 37;
      this.chkSouth.Text = "South";
      this.chkSouth.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.chkSouth.CheckedChanged += new System.EventHandler(this.chkSouth_CheckedChanged);
      // 
      // chkEast
      // 
      this.chkEast.BackColor = System.Drawing.SystemColors.Menu;
      this.chkEast.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkEast.BackgroundImage")));
      this.chkEast.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkEast.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkEast.ForeColor = System.Drawing.SystemColors.ControlText;
      this.chkEast.Location = new System.Drawing.Point(368, 424);
      this.chkEast.Name = "chkEast";
      this.chkEast.Size = new System.Drawing.Size(56, 16);
      this.chkEast.TabIndex = 38;
      this.chkEast.Text = "East";
      this.chkEast.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.chkEast.CheckedChanged += new System.EventHandler(this.chkEast_CheckedChanged);
      // 
      // chkWest
      // 
      this.chkWest.BackColor = System.Drawing.SystemColors.Menu;
      this.chkWest.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkWest.BackgroundImage")));
      this.chkWest.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkWest.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkWest.ForeColor = System.Drawing.SystemColors.ControlText;
      this.chkWest.Location = new System.Drawing.Point(368, 448);
      this.chkWest.Name = "chkWest";
      this.chkWest.Size = new System.Drawing.Size(56, 16);
      this.chkWest.TabIndex = 39;
      this.chkWest.Text = "West";
      this.chkWest.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.chkWest.CheckedChanged += new System.EventHandler(this.chkWest_CheckedChanged);
      // 
      // chkUp
      // 
      this.chkUp.BackColor = System.Drawing.SystemColors.Menu;
      this.chkUp.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkUp.BackgroundImage")));
      this.chkUp.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkUp.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkUp.ForeColor = System.Drawing.SystemColors.ControlText;
      this.chkUp.Location = new System.Drawing.Point(368, 472);
      this.chkUp.Name = "chkUp";
      this.chkUp.Size = new System.Drawing.Size(56, 16);
      this.chkUp.TabIndex = 40;
      this.chkUp.Text = "Up";
      this.chkUp.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.chkUp.CheckedChanged += new System.EventHandler(this.chkUp_CheckedChanged);
      // 
      // chkDown
      // 
      this.chkDown.BackColor = System.Drawing.SystemColors.Menu;
      this.chkDown.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkDown.BackgroundImage")));
      this.chkDown.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkDown.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkDown.ForeColor = System.Drawing.SystemColors.ControlText;
      this.chkDown.Location = new System.Drawing.Point(368, 496);
      this.chkDown.Name = "chkDown";
      this.chkDown.Size = new System.Drawing.Size(56, 16);
      this.chkDown.TabIndex = 41;
      this.chkDown.Text = "Down";
      this.chkDown.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.chkDown.CheckedChanged += new System.EventHandler(this.chkDown_CheckedChanged);
      // 
      // label4
      // 
      this.label4.BackColor = System.Drawing.Color.Lavender;
      this.label4.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label4.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label4.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label4.Location = new System.Drawing.Point(568, 208);
      this.label4.Name = "label4";
      this.label4.Size = new System.Drawing.Size(211, 16);
      this.label4.TabIndex = 43;
      this.label4.Text = "Mob List";
      this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label5
      // 
      this.label5.BackColor = System.Drawing.Color.Lavender;
      this.label5.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label5.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label5.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label5.Location = new System.Drawing.Point(568, 8);
      this.label5.Name = "label5";
      this.label5.Size = new System.Drawing.Size(211, 16);
      this.label5.TabIndex = 44;
      this.label5.Text = "Look List";
      this.label5.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label6
      // 
      this.label6.BackColor = System.Drawing.Color.Lavender;
      this.label6.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label6.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label6.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label6.Location = new System.Drawing.Point(352, 208);
      this.label6.Name = "label6";
      this.label6.Size = new System.Drawing.Size(211, 16);
      this.label6.TabIndex = 45;
      this.label6.Text = "Item List";
      this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // cmdNorthLook
      // 
      this.cmdNorthLook.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdNorthLook.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdNorthLook.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdNorthLook.Location = new System.Drawing.Point(504, 376);
      this.cmdNorthLook.Name = "cmdNorthLook";
      this.cmdNorthLook.Size = new System.Drawing.Size(88, 23);
      this.cmdNorthLook.TabIndex = 46;
      this.cmdNorthLook.Text = "North Look";
      this.cmdNorthLook.Click += new System.EventHandler(this.cmdNorthLook_Click);
      // 
      // cmdSouthLook
      // 
      this.cmdSouthLook.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdSouthLook.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdSouthLook.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdSouthLook.Location = new System.Drawing.Point(504, 400);
      this.cmdSouthLook.Name = "cmdSouthLook";
      this.cmdSouthLook.Size = new System.Drawing.Size(88, 23);
      this.cmdSouthLook.TabIndex = 47;
      this.cmdSouthLook.Text = "South Look";
      this.cmdSouthLook.Click += new System.EventHandler(this.cmdSouthLook_Click);
      // 
      // cmdEastLook
      // 
      this.cmdEastLook.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdEastLook.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdEastLook.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdEastLook.Location = new System.Drawing.Point(504, 424);
      this.cmdEastLook.Name = "cmdEastLook";
      this.cmdEastLook.Size = new System.Drawing.Size(88, 23);
      this.cmdEastLook.TabIndex = 48;
      this.cmdEastLook.Text = "East Look";
      this.cmdEastLook.Click += new System.EventHandler(this.cmdEastLook_Click);
      // 
      // cmdWestLook
      // 
      this.cmdWestLook.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdWestLook.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdWestLook.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdWestLook.Location = new System.Drawing.Point(504, 448);
      this.cmdWestLook.Name = "cmdWestLook";
      this.cmdWestLook.Size = new System.Drawing.Size(88, 23);
      this.cmdWestLook.TabIndex = 49;
      this.cmdWestLook.Text = "West Look";
      this.cmdWestLook.Click += new System.EventHandler(this.cmdWestLook_Click);
      // 
      // cmdUpLook
      // 
      this.cmdUpLook.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdUpLook.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdUpLook.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdUpLook.Location = new System.Drawing.Point(504, 472);
      this.cmdUpLook.Name = "cmdUpLook";
      this.cmdUpLook.Size = new System.Drawing.Size(88, 23);
      this.cmdUpLook.TabIndex = 50;
      this.cmdUpLook.Text = "Up Look";
      this.cmdUpLook.Click += new System.EventHandler(this.cmdUpLook_Click);
      // 
      // cmdDownLook
      // 
      this.cmdDownLook.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdDownLook.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdDownLook.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdDownLook.Location = new System.Drawing.Point(504, 496);
      this.cmdDownLook.Name = "cmdDownLook";
      this.cmdDownLook.Size = new System.Drawing.Size(88, 23);
      this.cmdDownLook.TabIndex = 51;
      this.cmdDownLook.Text = "Down Look";
      this.cmdDownLook.Click += new System.EventHandler(this.cmdDownLook_Click);
      // 
      // chkDONATION
      // 
      this.chkDONATION.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkDONATION.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkDONATION.BackgroundImage")));
      this.chkDONATION.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkDONATION.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkDONATION.Location = new System.Drawing.Point(8, 456);
      this.chkDONATION.Name = "chkDONATION";
      this.chkDONATION.Size = new System.Drawing.Size(88, 16);
      this.chkDONATION.TabIndex = 63;
      this.chkDONATION.Text = "DONATION";
      // 
      // chkNOMOB
      // 
      this.chkNOMOB.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkNOMOB.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNOMOB.BackgroundImage")));
      this.chkNOMOB.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNOMOB.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNOMOB.Location = new System.Drawing.Point(280, 376);
      this.chkNOMOB.Name = "chkNOMOB";
      this.chkNOMOB.Size = new System.Drawing.Size(88, 16);
      this.chkNOMOB.TabIndex = 62;
      this.chkNOMOB.Text = "NO MOB";
      // 
      // chkNODROP
      // 
      this.chkNODROP.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkNODROP.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNODROP.BackgroundImage")));
      this.chkNODROP.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNODROP.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNODROP.Location = new System.Drawing.Point(280, 408);
      this.chkNODROP.Name = "chkNODROP";
      this.chkNODROP.Size = new System.Drawing.Size(88, 16);
      this.chkNODROP.TabIndex = 61;
      this.chkNODROP.Text = "NO DROP";
      // 
      // chkPRIVATE
      // 
      this.chkPRIVATE.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkPRIVATE.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkPRIVATE.BackgroundImage")));
      this.chkPRIVATE.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkPRIVATE.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkPRIVATE.Location = new System.Drawing.Point(8, 472);
      this.chkPRIVATE.Name = "chkPRIVATE";
      this.chkPRIVATE.Size = new System.Drawing.Size(88, 16);
      this.chkPRIVATE.TabIndex = 60;
      this.chkPRIVATE.Text = "PRIVATE";
      // 
      // chkSILENT
      // 
      this.chkSILENT.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkSILENT.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkSILENT.BackgroundImage")));
      this.chkSILENT.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkSILENT.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkSILENT.Location = new System.Drawing.Point(8, 408);
      this.chkSILENT.Name = "chkSILENT";
      this.chkSILENT.Size = new System.Drawing.Size(88, 16);
      this.chkSILENT.TabIndex = 59;
      this.chkSILENT.Text = "SILENT";
      // 
      // chkNOQUIT
      // 
      this.chkNOQUIT.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkNOQUIT.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNOQUIT.BackgroundImage")));
      this.chkNOQUIT.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNOQUIT.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNOQUIT.Location = new System.Drawing.Point(280, 392);
      this.chkNOQUIT.Name = "chkNOQUIT";
      this.chkNOQUIT.Size = new System.Drawing.Size(72, 16);
      this.chkNOQUIT.TabIndex = 58;
      this.chkNOQUIT.Text = "NO QUIT";
      // 
      // chkNOSUMMON
      // 
      this.chkNOSUMMON.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkNOSUMMON.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNOSUMMON.BackgroundImage")));
      this.chkNOSUMMON.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNOSUMMON.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNOSUMMON.Location = new System.Drawing.Point(280, 472);
      this.chkNOSUMMON.Name = "chkNOSUMMON";
      this.chkNOSUMMON.Size = new System.Drawing.Size(88, 16);
      this.chkNOSUMMON.TabIndex = 57;
      this.chkNOSUMMON.Text = "NO SUMMON";
      // 
      // chkINDOORS
      // 
      this.chkINDOORS.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkINDOORS.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkINDOORS.BackgroundImage")));
      this.chkINDOORS.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkINDOORS.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkINDOORS.Location = new System.Drawing.Point(8, 392);
      this.chkINDOORS.Name = "chkINDOORS";
      this.chkINDOORS.Size = new System.Drawing.Size(72, 16);
      this.chkINDOORS.TabIndex = 56;
      this.chkINDOORS.Text = "INDOORS";
      // 
      // chkRegenHP
      // 
      this.chkRegenHP.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkRegenHP.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkRegenHP.BackgroundImage")));
      this.chkRegenHP.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkRegenHP.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkRegenHP.Location = new System.Drawing.Point(184, 392);
      this.chkRegenHP.Name = "chkRegenHP";
      this.chkRegenHP.Size = new System.Drawing.Size(88, 16);
      this.chkRegenHP.TabIndex = 55;
      this.chkRegenHP.Text = "REGEN HP";
      // 
      // chkNOMAGIC
      // 
      this.chkNOMAGIC.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkNOMAGIC.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNOMAGIC.BackgroundImage")));
      this.chkNOMAGIC.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNOMAGIC.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNOMAGIC.Location = new System.Drawing.Point(280, 424);
      this.chkNOMAGIC.Name = "chkNOMAGIC";
      this.chkNOMAGIC.Size = new System.Drawing.Size(88, 16);
      this.chkNOMAGIC.TabIndex = 54;
      this.chkNOMAGIC.Text = "NO MAGIC";
      // 
      // chkLAWFULL
      // 
      this.chkLAWFULL.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkLAWFULL.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkLAWFULL.BackgroundImage")));
      this.chkLAWFULL.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkLAWFULL.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkLAWFULL.Location = new System.Drawing.Point(8, 424);
      this.chkLAWFULL.Name = "chkLAWFULL";
      this.chkLAWFULL.Size = new System.Drawing.Size(88, 16);
      this.chkLAWFULL.TabIndex = 53;
      this.chkLAWFULL.Text = "LAWFULL";
      // 
      // chkPKOK
      // 
      this.chkPKOK.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkPKOK.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkPKOK.BackgroundImage")));
      this.chkPKOK.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkPKOK.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkPKOK.Location = new System.Drawing.Point(8, 440);
      this.chkPKOK.Name = "chkPKOK";
      this.chkPKOK.Size = new System.Drawing.Size(88, 16);
      this.chkPKOK.TabIndex = 52;
      this.chkPKOK.Text = "PK_OK";
      // 
      // label7
      // 
      this.label7.BackColor = System.Drawing.Color.Lavender;
      this.label7.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label7.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label7.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label7.Location = new System.Drawing.Point(352, 8);
      this.label7.Name = "label7";
      this.label7.Size = new System.Drawing.Size(211, 16);
      this.label7.TabIndex = 64;
      this.label7.Text = "Room Actions";
      this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label8
      // 
      this.label8.BackColor = System.Drawing.Color.Lavender;
      this.label8.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label8.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label8.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label8.Location = new System.Drawing.Point(8, 352);
      this.label8.Name = "label8";
      this.label8.Size = new System.Drawing.Size(352, 16);
      this.label8.TabIndex = 65;
      this.label8.Text = "Flags";
      this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // label9
      // 
      this.label9.BackColor = System.Drawing.Color.Lavender;
      this.label9.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label9.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label9.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label9.Location = new System.Drawing.Point(368, 352);
      this.label9.Name = "label9";
      this.label9.Size = new System.Drawing.Size(408, 16);
      this.label9.TabIndex = 66;
      this.label9.Text = "Exits";
      this.label9.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // lstActions
      // 
      this.lstActions.BackColor = System.Drawing.SystemColors.Menu;
      this.lstActions.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstActions.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.lstActions.ForeColor = System.Drawing.SystemColors.ControlText;
      this.lstActions.ItemHeight = 14;
      this.lstActions.Location = new System.Drawing.Point(352, 32);
      this.lstActions.Name = "lstActions";
      this.lstActions.Size = new System.Drawing.Size(211, 86);
      this.lstActions.TabIndex = 67;
      this.lstActions.SelectedIndexChanged += new System.EventHandler(this.lstActions_SelectedIndexChanged);
      // 
      // cmdAddAction
      // 
      this.cmdAddAction.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddAction.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddAction.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdAddAction.Location = new System.Drawing.Point(352, 176);
      this.cmdAddAction.Name = "cmdAddAction";
      this.cmdAddAction.TabIndex = 68;
      this.cmdAddAction.Text = "Add";
      this.cmdAddAction.Click += new System.EventHandler(this.cmdAddAction_Click);
      // 
      // cmdRemoveAction
      // 
      this.cmdRemoveAction.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdRemoveAction.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdRemoveAction.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdRemoveAction.Location = new System.Drawing.Point(488, 176);
      this.cmdRemoveAction.Name = "cmdRemoveAction";
      this.cmdRemoveAction.TabIndex = 69;
      this.cmdRemoveAction.Text = "Remove";
      this.cmdRemoveAction.Click += new System.EventHandler(this.cmdRemoveAction_Click);
      // 
      // txtActionFreq
      // 
      this.txtActionFreq.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtActionFreq.Location = new System.Drawing.Point(408, 152);
      this.txtActionFreq.Name = "txtActionFreq";
      this.txtActionFreq.Size = new System.Drawing.Size(96, 20);
      this.txtActionFreq.TabIndex = 96;
      this.txtActionFreq.Text = "";
      // 
      // txtAction
      // 
      this.txtAction.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtAction.Location = new System.Drawing.Point(408, 128);
      this.txtAction.Name = "txtAction";
      this.txtAction.Size = new System.Drawing.Size(155, 20);
      this.txtAction.TabIndex = 95;
      this.txtAction.Text = "";
      // 
      // label37
      // 
      this.label37.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label37.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label37.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label37.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label37.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label37.Location = new System.Drawing.Point(352, 152);
      this.label37.Name = "label37";
      this.label37.Size = new System.Drawing.Size(48, 16);
      this.label37.TabIndex = 94;
      this.label37.Text = "Freq:";
      this.label37.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label36
      // 
      this.label36.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label36.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label36.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label36.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label36.ImageAlign = System.Drawing.ContentAlignment.MiddleRight;
      this.label36.Location = new System.Drawing.Point(352, 128);
      this.label36.Name = "label36";
      this.label36.Size = new System.Drawing.Size(48, 16);
      this.label36.TabIndex = 93;
      this.label36.Text = "Name:";
      this.label36.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // cmdLinkNorth
      // 
      this.cmdLinkNorth.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdLinkNorth.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdLinkNorth.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdLinkNorth.Location = new System.Drawing.Point(600, 376);
      this.cmdLinkNorth.Name = "cmdLinkNorth";
      this.cmdLinkNorth.Size = new System.Drawing.Size(88, 23);
      this.cmdLinkNorth.TabIndex = 97;
      this.cmdLinkNorth.Text = "Link North";
      this.cmdLinkNorth.Click += new System.EventHandler(this.create_link);
      // 
      // cmdLinkSouth
      // 
      this.cmdLinkSouth.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdLinkSouth.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdLinkSouth.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdLinkSouth.Location = new System.Drawing.Point(600, 400);
      this.cmdLinkSouth.Name = "cmdLinkSouth";
      this.cmdLinkSouth.Size = new System.Drawing.Size(88, 23);
      this.cmdLinkSouth.TabIndex = 98;
      this.cmdLinkSouth.Text = "Link South";
      this.cmdLinkSouth.Click += new System.EventHandler(this.create_link);
      // 
      // cmdLinkEast
      // 
      this.cmdLinkEast.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdLinkEast.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdLinkEast.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdLinkEast.Location = new System.Drawing.Point(600, 424);
      this.cmdLinkEast.Name = "cmdLinkEast";
      this.cmdLinkEast.Size = new System.Drawing.Size(88, 23);
      this.cmdLinkEast.TabIndex = 99;
      this.cmdLinkEast.Text = "Link East";
      this.cmdLinkEast.Click += new System.EventHandler(this.create_link);
      // 
      // cmdLinkWest
      // 
      this.cmdLinkWest.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdLinkWest.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdLinkWest.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdLinkWest.Location = new System.Drawing.Point(600, 448);
      this.cmdLinkWest.Name = "cmdLinkWest";
      this.cmdLinkWest.Size = new System.Drawing.Size(88, 23);
      this.cmdLinkWest.TabIndex = 100;
      this.cmdLinkWest.Text = "Link West";
      this.cmdLinkWest.Click += new System.EventHandler(this.create_link);
      // 
      // cmdLinkUp
      // 
      this.cmdLinkUp.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdLinkUp.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdLinkUp.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdLinkUp.Location = new System.Drawing.Point(600, 472);
      this.cmdLinkUp.Name = "cmdLinkUp";
      this.cmdLinkUp.Size = new System.Drawing.Size(88, 23);
      this.cmdLinkUp.TabIndex = 101;
      this.cmdLinkUp.Text = "Link Up";
      this.cmdLinkUp.Click += new System.EventHandler(this.create_link);
      // 
      // cmdLinkDown
      // 
      this.cmdLinkDown.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdLinkDown.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdLinkDown.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdLinkDown.Location = new System.Drawing.Point(600, 496);
      this.cmdLinkDown.Name = "cmdLinkDown";
      this.cmdLinkDown.Size = new System.Drawing.Size(88, 23);
      this.cmdLinkDown.TabIndex = 102;
      this.cmdLinkDown.Text = "Link Down";
      this.cmdLinkDown.Click += new System.EventHandler(this.create_link);
      // 
      // cmdReverseLink
      // 
      this.cmdReverseLink.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdReverseLink.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdReverseLink.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdReverseLink.Location = new System.Drawing.Point(696, 376);
      this.cmdReverseLink.Name = "cmdReverseLink";
      this.cmdReverseLink.Size = new System.Drawing.Size(80, 32);
      this.cmdReverseLink.TabIndex = 102;
      this.cmdReverseLink.Text = "Incoming Link All";
      this.cmdReverseLink.Click += new System.EventHandler(this.reverse_link);
      // 
      // cmdLinkAll
      // 
      this.cmdLinkAll.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdLinkAll.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdLinkAll.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdLinkAll.Location = new System.Drawing.Point(696, 416);
      this.cmdLinkAll.Name = "cmdLinkAll";
      this.cmdLinkAll.Size = new System.Drawing.Size(80, 32);
      this.cmdLinkAll.TabIndex = 102;
      this.cmdLinkAll.Text = "Outgoing Link All";
      this.cmdLinkAll.Click += new System.EventHandler(this.all_link);
      // 
      // chkDark
      // 
      this.chkDark.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkDark.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkDark.BackgroundImage")));
      this.chkDark.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkDark.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkDark.Location = new System.Drawing.Point(88, 376);
      this.chkDark.Name = "chkDark";
      this.chkDark.Size = new System.Drawing.Size(72, 16);
      this.chkDark.TabIndex = 103;
      this.chkDark.Text = "DARK";
      // 
      // chkChurch
      // 
      this.chkChurch.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkChurch.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkChurch.BackgroundImage")));
      this.chkChurch.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkChurch.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkChurch.Location = new System.Drawing.Point(88, 408);
      this.chkChurch.Name = "chkChurch";
      this.chkChurch.Size = new System.Drawing.Size(88, 16);
      this.chkChurch.TabIndex = 104;
      this.chkChurch.Text = "CHURCH";
      // 
      // chkTunnel
      // 
      this.chkTunnel.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkTunnel.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkTunnel.BackgroundImage")));
      this.chkTunnel.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkTunnel.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkTunnel.Location = new System.Drawing.Point(88, 424);
      this.chkTunnel.Name = "chkTunnel";
      this.chkTunnel.Size = new System.Drawing.Size(88, 16);
      this.chkTunnel.TabIndex = 105;
      this.chkTunnel.Text = "TUNNEL";
      // 
      // chkDamage
      // 
      this.chkDamage.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkDamage.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkDamage.BackgroundImage")));
      this.chkDamage.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkDamage.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkDamage.Location = new System.Drawing.Point(88, 456);
      this.chkDamage.Name = "chkDamage";
      this.chkDamage.Size = new System.Drawing.Size(88, 16);
      this.chkDamage.TabIndex = 106;
      this.chkDamage.Text = "DAMAGE";
      // 
      // chkDeathTrap
      // 
      this.chkDeathTrap.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkDeathTrap.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkDeathTrap.BackgroundImage")));
      this.chkDeathTrap.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkDeathTrap.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkDeathTrap.Location = new System.Drawing.Point(88, 472);
      this.chkDeathTrap.Name = "chkDeathTrap";
      this.chkDeathTrap.Size = new System.Drawing.Size(96, 16);
      this.chkDeathTrap.TabIndex = 107;
      this.chkDeathTrap.Text = "DEATHTRAP";
      // 
      // chkUnderwater
      // 
      this.chkUnderwater.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkUnderwater.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkUnderwater.BackgroundImage")));
      this.chkUnderwater.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkUnderwater.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkUnderwater.Location = new System.Drawing.Point(88, 440);
      this.chkUnderwater.Name = "chkUnderwater";
      this.chkUnderwater.Size = new System.Drawing.Size(96, 16);
      this.chkUnderwater.TabIndex = 108;
      this.chkUnderwater.Text = "UNDERWATER";
      // 
      // chkGodRoom
      // 
      this.chkGodRoom.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkGodRoom.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkGodRoom.BackgroundImage")));
      this.chkGodRoom.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkGodRoom.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkGodRoom.Location = new System.Drawing.Point(184, 376);
      this.chkGodRoom.Name = "chkGodRoom";
      this.chkGodRoom.Size = new System.Drawing.Size(72, 16);
      this.chkGodRoom.TabIndex = 109;
      this.chkGodRoom.Text = "GODROOM";
      // 
      // chkShroud
      // 
      this.chkShroud.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkShroud.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkShroud.BackgroundImage")));
      this.chkShroud.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkShroud.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkShroud.Location = new System.Drawing.Point(88, 392);
      this.chkShroud.Name = "chkShroud";
      this.chkShroud.Size = new System.Drawing.Size(64, 16);
      this.chkShroud.TabIndex = 110;
      this.chkShroud.Text = "SHROUD";
      // 
      // chkNoTrack
      // 
      this.chkNoTrack.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkNoTrack.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNoTrack.BackgroundImage")));
      this.chkNoTrack.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNoTrack.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNoTrack.Location = new System.Drawing.Point(184, 424);
      this.chkNoTrack.Name = "chkNoTrack";
      this.chkNoTrack.Size = new System.Drawing.Size(96, 16);
      this.chkNoTrack.TabIndex = 111;
      this.chkNoTrack.Text = "NO_TRACK";
      // 
      // chkNoScout
      // 
      this.chkNoScout.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkNoScout.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNoScout.BackgroundImage")));
      this.chkNoScout.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNoScout.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNoScout.Location = new System.Drawing.Point(184, 440);
      this.chkNoScout.Name = "chkNoScout";
      this.chkNoScout.Size = new System.Drawing.Size(96, 16);
      this.chkNoScout.TabIndex = 112;
      this.chkNoScout.Text = "NO_SCOUT";
      // 
      // chkNoDispel
      // 
      this.chkNoDispel.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkNoDispel.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNoDispel.BackgroundImage")));
      this.chkNoDispel.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNoDispel.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNoDispel.Location = new System.Drawing.Point(184, 456);
      this.chkNoDispel.Name = "chkNoDispel";
      this.chkNoDispel.Size = new System.Drawing.Size(96, 16);
      this.chkNoDispel.TabIndex = 113;
      this.chkNoDispel.Text = "NO_DISPEL";
      // 
      // chkNoTeleport
      // 
      this.chkNoTeleport.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkNoTeleport.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNoTeleport.BackgroundImage")));
      this.chkNoTeleport.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNoTeleport.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNoTeleport.Location = new System.Drawing.Point(184, 472);
      this.chkNoTeleport.Name = "chkNoTeleport";
      this.chkNoTeleport.Size = new System.Drawing.Size(96, 16);
      this.chkNoTeleport.TabIndex = 114;
      this.chkNoTeleport.Text = "NO_TELEPORT";
      // 
      // chkNoFlee
      // 
      this.chkNoFlee.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkNoFlee.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNoFlee.BackgroundImage")));
      this.chkNoFlee.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNoFlee.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNoFlee.Location = new System.Drawing.Point(280, 440);
      this.chkNoFlee.Name = "chkNoFlee";
      this.chkNoFlee.Size = new System.Drawing.Size(72, 16);
      this.chkNoFlee.TabIndex = 115;
      this.chkNoFlee.Text = "NO_FLEE";
      // 
      // chkAnimate
      // 
      this.chkAnimate.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkAnimate.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkAnimate.BackgroundImage")));
      this.chkAnimate.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkAnimate.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkAnimate.Location = new System.Drawing.Point(8, 376);
      this.chkAnimate.Name = "chkAnimate";
      this.chkAnimate.Size = new System.Drawing.Size(72, 16);
      this.chkAnimate.TabIndex = 116;
      this.chkAnimate.Text = "ANIMATE";
      // 
      // chkNoSpell
      // 
      this.chkNoSpell.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkNoSpell.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkNoSpell.BackgroundImage")));
      this.chkNoSpell.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkNoSpell.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkNoSpell.Location = new System.Drawing.Point(280, 456);
      this.chkNoSpell.Name = "chkNoSpell";
      this.chkNoSpell.Size = new System.Drawing.Size(80, 16);
      this.chkNoSpell.TabIndex = 117;
      this.chkNoSpell.Text = "NO_SPELL";
      // 
      // chkRegenMN
      // 
      this.chkRegenMN.BackColor = System.Drawing.SystemColors.HighlightText;
      this.chkRegenMN.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkRegenMN.BackgroundImage")));
      this.chkRegenMN.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkRegenMN.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.chkRegenMN.Location = new System.Drawing.Point(184, 408);
      this.chkRegenMN.Name = "chkRegenMN";
      this.chkRegenMN.Size = new System.Drawing.Size(88, 16);
      this.chkRegenMN.TabIndex = 118;
      this.chkRegenMN.Text = "REGEN MN";
      // 
      // cmdDoubleLink
      // 
      this.cmdDoubleLink.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdDoubleLink.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdDoubleLink.ForeColor = System.Drawing.SystemColors.ControlText;
      this.cmdDoubleLink.Location = new System.Drawing.Point(696, 456);
      this.cmdDoubleLink.Name = "cmdDoubleLink";
      this.cmdDoubleLink.Size = new System.Drawing.Size(80, 63);
      this.cmdDoubleLink.TabIndex = 119;
      this.cmdDoubleLink.Text = "LINK  ALL";
      this.cmdDoubleLink.Click += new System.EventHandler(this.cmdDoubleLink_Click);
      // 
      // label10
      // 
      this.label10.BackColor = System.Drawing.Color.Lavender;
      this.label10.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label10.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label10.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label10.Location = new System.Drawing.Point(8, 496);
      this.label10.Name = "label10";
      this.label10.Size = new System.Drawing.Size(288, 16);
      this.label10.TabIndex = 120;
      this.label10.Text = "Terrain";
      this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
      // 
      // cboTerrain
      // 
      this.cboTerrain.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
      this.cboTerrain.Items.AddRange(new object[] {
                                                    "Flat Terrain Normal",
                                                    "Flat Terrain Icy",
                                                    "Flat Terrain Muddy",
                                                    "Flat Terrain Rocky",
                                                    "Road Dirt",
                                                    "Road Stone",
                                                    "Mountain Level",
                                                    "Mountain Hills",
                                                    "Mountain Ascending",
                                                    "Mountain Steep",
                                                    "Forest Brush",
                                                    "Forest Sparse",
                                                    "Forest Dense",
                                                    "Swamp Light",
                                                    "Swamp Boggy",
                                                    "Water Shallow",
                                                    "Water Deep",
                                                    "Water Sea",
                                                    "Special Airborne",
                                                    "Special Shimmy",
                                                    "Special Repel",
                                                    "Special Crawl"});
      this.cboTerrain.Location = new System.Drawing.Point(8, 520);
      this.cboTerrain.Name = "cboTerrain";
      this.cboTerrain.Size = new System.Drawing.Size(288, 22);
      this.cboTerrain.TabIndex = 121;
      // 
      // chkExact
      // 
      this.chkExact.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("chkExact.BackgroundImage")));
      this.chkExact.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.chkExact.Location = new System.Drawing.Point(22, 184);
      this.chkExact.Name = "chkExact";
      this.chkExact.Size = new System.Drawing.Size(16, 16);
      this.chkExact.TabIndex = 146;
      this.chkExact.CheckedChanged += new System.EventHandler(this.chkExact_CheckedChanged);
      // 
      // cmdExact
      // 
      this.cmdExact.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdExact.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdExact.Font = new System.Drawing.Font("Courier New", 9.75F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.cmdExact.Location = new System.Drawing.Point(16, 88);
      this.cmdExact.Name = "cmdExact";
      this.cmdExact.Size = new System.Drawing.Size(24, 88);
      this.cmdExact.TabIndex = 145;
      this.cmdExact.Text = "Exact";
      this.cmdExact.Click += new System.EventHandler(this.cmdExact_Click);
      // 
      // frmRoomEditor
      // 
      this.AutoScaleBaseSize = new System.Drawing.Size(7, 13);
      this.BackColor = System.Drawing.SystemColors.Menu;
      this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
      this.ClientSize = new System.Drawing.Size(782, 556);
      this.Controls.Add(this.chkExact);
      this.Controls.Add(this.cmdExact);
      this.Controls.Add(this.cboTerrain);
      this.Controls.Add(this.label10);
      this.Controls.Add(this.cmdDoubleLink);
      this.Controls.Add(this.chkRegenMN);
      this.Controls.Add(this.chkNoSpell);
      this.Controls.Add(this.chkAnimate);
      this.Controls.Add(this.chkNoFlee);
      this.Controls.Add(this.chkNoTeleport);
      this.Controls.Add(this.chkNoDispel);
      this.Controls.Add(this.chkNoScout);
      this.Controls.Add(this.chkNoTrack);
      this.Controls.Add(this.chkShroud);
      this.Controls.Add(this.chkGodRoom);
      this.Controls.Add(this.chkUnderwater);
      this.Controls.Add(this.chkDeathTrap);
      this.Controls.Add(this.chkDamage);
      this.Controls.Add(this.chkTunnel);
      this.Controls.Add(this.chkChurch);
      this.Controls.Add(this.chkDark);
      this.Controls.Add(this.cmdLinkAll);
      this.Controls.Add(this.cmdReverseLink);
      this.Controls.Add(this.cmdLinkDown);
      this.Controls.Add(this.cmdLinkUp);
      this.Controls.Add(this.cmdLinkWest);
      this.Controls.Add(this.cmdLinkEast);
      this.Controls.Add(this.cmdLinkSouth);
      this.Controls.Add(this.cmdLinkNorth);
      this.Controls.Add(this.txtActionFreq);
      this.Controls.Add(this.txtAction);
      this.Controls.Add(this.txtDown);
      this.Controls.Add(this.txtUp);
      this.Controls.Add(this.txtWest);
      this.Controls.Add(this.txtEast);
      this.Controls.Add(this.txtSouth);
      this.Controls.Add(this.txtNorth);
      this.Controls.Add(this.txtDesc);
      this.Controls.Add(this.txtName);
      this.Controls.Add(this.txtID);
      this.Controls.Add(this.label37);
      this.Controls.Add(this.label36);
      this.Controls.Add(this.cmdRemoveAction);
      this.Controls.Add(this.cmdAddAction);
      this.Controls.Add(this.lstActions);
      this.Controls.Add(this.label9);
      this.Controls.Add(this.label8);
      this.Controls.Add(this.label7);
      this.Controls.Add(this.chkDONATION);
      this.Controls.Add(this.chkNOMOB);
      this.Controls.Add(this.chkNODROP);
      this.Controls.Add(this.chkPRIVATE);
      this.Controls.Add(this.chkSILENT);
      this.Controls.Add(this.chkNOQUIT);
      this.Controls.Add(this.chkNOSUMMON);
      this.Controls.Add(this.chkINDOORS);
      this.Controls.Add(this.chkRegenHP);
      this.Controls.Add(this.chkNOMAGIC);
      this.Controls.Add(this.chkLAWFULL);
      this.Controls.Add(this.chkPKOK);
      this.Controls.Add(this.cmdDownLook);
      this.Controls.Add(this.cmdUpLook);
      this.Controls.Add(this.cmdWestLook);
      this.Controls.Add(this.cmdEastLook);
      this.Controls.Add(this.cmdSouthLook);
      this.Controls.Add(this.cmdNorthLook);
      this.Controls.Add(this.label6);
      this.Controls.Add(this.label5);
      this.Controls.Add(this.label4);
      this.Controls.Add(this.chkDown);
      this.Controls.Add(this.chkUp);
      this.Controls.Add(this.chkWest);
      this.Controls.Add(this.chkEast);
      this.Controls.Add(this.chkSouth);
      this.Controls.Add(this.chkNorth);
      this.Controls.Add(this.cmdCancel);
      this.Controls.Add(this.cmdSave);
      this.Controls.Add(this.cmdRemoveLook);
      this.Controls.Add(this.cmdEditLook);
      this.Controls.Add(this.cmdAddLook);
      this.Controls.Add(this.lstLook);
      this.Controls.Add(this.cmdRemoveItem);
      this.Controls.Add(this.cmdAddItem);
      this.Controls.Add(this.lstItem);
      this.Controls.Add(this.cmdRemoveMob);
      this.Controls.Add(this.cmdAddMob);
      this.Controls.Add(this.lstMob);
      this.Controls.Add(this.label3);
      this.Controls.Add(this.label2);
      this.Controls.Add(this.label1);
      this.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
      this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
      this.MaximizeBox = false;
      this.MinimizeBox = false;
      this.Name = "frmRoomEditor";
      this.ShowInTaskbar = false;
      this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
      this.Text = "Tempest Room Editor";
      this.ResumeLayout(false);

    }

    public void set_activeroom(room room_pointer)

    {
      active_room  = room_pointer;
      original_id  = active_room.get_id();
      txtID.Text   = active_room.get_id();
      txtName.Text = active_room.get_name();
      
      if (room_pointer.exact)
      
      {
        chkExact.Checked = true;
        txtDesc.ReadOnly = true;
        txtDesc.Text = "EXACT DESCRIPTION";
        txtDesc.BackColor = Color.FromName("Control");
        exact_holder = room_pointer.exact_description;
      }
      
      else
      
      {
        txtDesc.ReadOnly = false;
        txtDesc.BackColor = Color.FromName("Window");
        txtDesc.Text = active_room.get_desc();
      }
      
      keys      = new ArrayList(active_room.get_keys());
      descs     = new ArrayList(active_room.get_descs());
      exitkeys  = new ArrayList(active_room.get_exitkeys());
      exitdescs = new ArrayList(active_room.get_exitdescs());
      actions   = new ArrayList(active_room.get_actions());
      mlist     = (moblist)active_room.get_mobs().Clone();
      ilist     = (itemlist)active_room.get_ilist().Clone();

      chkPKOK.Checked     = active_room.get_pkok();
      chkLAWFULL.Checked  = active_room.get_lawfull();
      chkNOMAGIC.Checked  = active_room.get_nomagic();
      chkINDOORS.Checked  = active_room.get_indoors();
      chkNOSUMMON.Checked = active_room.get_nosummon();
      chkNOQUIT.Checked   = active_room.get_noquit();
      chkSILENT.Checked   = active_room.get_silent();
      chkPRIVATE.Checked  = active_room.get_private();
      chkNODROP.Checked   = active_room.get_nodrop();
      chkNOMOB.Checked    = active_room.get_nomob();
      chkDONATION.Checked = active_room.get_donation();
      chkDark.Checked     = active_room.DARK;
      chkChurch.Checked   = active_room.CHURCH;
      chkTunnel.Checked   = active_room.TUNNEL;
      chkDamage.Checked   = active_room.DAMAGE;
      chkDeathTrap.Checked = active_room.DEATHTRAP;
      chkUnderwater.Checked = active_room.UNDERWATER;
      chkGodRoom.Checked  = active_room.GODROOM;
      chkShroud.Checked   = active_room.SHROUD;
      chkNoTrack.Checked  = active_room.NO_TRACK;
      chkNoScout.Checked  = active_room.NO_SCOUT;
      chkNoDispel.Checked = active_room.NO_DISPEL;
      chkNoTeleport.Checked = active_room.NO_TELEPORT;
      chkNoFlee.Checked   = active_room.NO_FLEE;
      chkNoSpell.Checked  = active_room.NO_SPELL;
      chkAnimate.Checked  = active_room.ANIMATE;
      chkRegenHP.Checked  = active_room.REGEN_HP;
      chkRegenMN.Checked  = active_room.REGEN_MN;
      
      cboTerrain.SelectedItem = active_room.terrain;

      #region Direction Initialization
      if (active_room.get_exit(0) != null)

      {
        chkNorth.Checked = true;
        txtNorth.ReadOnly = false;
        txtNorth.Text = active_room.get_exit(0);
      }

      else

      {
        chkNorth.Checked = false;
        txtNorth.ReadOnly = true;
        txtNorth.Text = "";
      }

      if (active_room.get_exit(1) != null)

      {
        chkSouth.Checked = true;
        txtSouth.ReadOnly = false;
        txtSouth.Text = active_room.get_exit(1);
      }

      else

      {
        chkSouth.Checked = false;
        txtSouth.ReadOnly = true;
        txtSouth.Text = "";
      }

      if (active_room.get_exit(2) != null)

      {
        chkEast.Checked = true;
        txtEast.ReadOnly = false;
        txtEast.Text = active_room.get_exit(2);
      }

      else

      {
        chkEast.Checked = false;
        txtEast.ReadOnly = true;
        txtEast.Text = "";
      }

      if (active_room.get_exit(3) != null)

      {
        chkWest.Checked = true;
        txtWest.ReadOnly = false;
        txtWest.Text = active_room.get_exit(3);
      }

      else

      {
        chkWest.Checked = false;
        txtWest.ReadOnly = true;
        txtWest.Text = "";
      }

      if (active_room.get_exit(4) != null)

      {
        chkUp.Checked = true;
        txtUp.ReadOnly = false;
        txtUp.Text = active_room.get_exit(4);
      }

      else

      {
        chkUp.Checked = false;
        txtUp.ReadOnly = true;
        txtUp.Text = "";
      }

      if (active_room.get_exit(5) != null)

      {
        chkDown.Checked = true;
        txtDown.ReadOnly = false;
        txtDown.Text = active_room.get_exit(5);
      }

      else

      {
        chkDown.Checked = false;
        txtDown.ReadOnly = true;
        txtDown.Text = "";
      }
      #endregion

      lstLook.Items.Clear();

      update_ilist();
      update_moblist();
      update_actionslist();

      for (int i=0; i<keys.Count; i++)
        lstLook.Items.Add((string)keys[i]);

      active = true;
      changed = false;
    }

    public void save_room()

    {
      active_room.set_id(Utility.clear_whitespace(txtID.Text));
      active_room.set_name(Utility.clear_whitespace(txtName.Text));
      
      if (chkExact.Checked)
      
      {
        active_room.exact = true;
        active_room.exact_description = exact_holder;
      }
      
      else
      
      {
        active_room.exact = false;
        active_room.exact_description = "";
      }
      
      active_room.set_desc(Utility.clear_whitespace(txtDesc.Text));

      if (chkNorth.Checked) active_room.set_exit(0, txtNorth.Text); else active_room.set_exit(0, null);
      if (chkSouth.Checked) active_room.set_exit(1, txtSouth.Text); else active_room.set_exit(1, null);
      if (chkEast.Checked)  active_room.set_exit(2, txtEast.Text);  else active_room.set_exit(2, null);
      if (chkWest.Checked)  active_room.set_exit(3, txtWest.Text);  else active_room.set_exit(3, null);
      if (chkUp.Checked)    active_room.set_exit(4, txtUp.Text);    else active_room.set_exit(4, null);
      if (chkDown.Checked)  active_room.set_exit(5, txtDown.Text);  else active_room.set_exit(5, null);

      active_room.set_pkok(chkPKOK.Checked);
      active_room.set_lawfull(chkLAWFULL.Checked);
      active_room.set_nomagic(chkNOMAGIC.Checked);
      active_room.set_indoors(chkINDOORS.Checked);
      active_room.set_nosummon(chkNOSUMMON.Checked);
      active_room.set_noquit(chkNOQUIT.Checked);
      active_room.set_silent(chkSILENT.Checked);
      active_room.set_private(chkPRIVATE.Checked);
      active_room.set_nodrop(chkNODROP.Checked);
      active_room.set_nomob(chkNOMOB.Checked);
      active_room.set_donation(chkDONATION.Checked);
      active_room.DARK = chkDark.Checked;
      active_room.CHURCH = chkChurch.Checked;
      active_room.TUNNEL = chkTunnel.Checked;
      active_room.DAMAGE = chkDamage.Checked;
      active_room.DEATHTRAP = chkDeathTrap.Checked;
      active_room.UNDERWATER = chkUnderwater.Checked;
      active_room.GODROOM = chkGodRoom.Checked;
      active_room.SHROUD = chkShroud.Checked;
      active_room.NO_TRACK = chkNoTrack.Checked;
      active_room.NO_SCOUT = chkNoScout.Checked;
      active_room.NO_DISPEL = chkNoDispel.Checked;
      active_room.NO_TELEPORT = chkNoTeleport.Checked;
      active_room.NO_FLEE = chkNoFlee.Checked;
      active_room.NO_SPELL = chkNoSpell.Checked;
      active_room.ANIMATE = chkAnimate.Checked;
      active_room.REGEN_HP = chkRegenHP.Checked;
      active_room.REGEN_MN = chkRegenMN.Checked;
      
      active_room.terrain = cboTerrain.Text;

      active_room.set_keys(keys);
      active_room.set_descs(descs);
      active_room.set_exitkeys(exitkeys);
      active_room.set_exitdescs(exitdescs);
      active_room.set_ilist(ilist);
      active_room.set_actions(actions);
      active_room.set_mobs(mlist);
    }

    private void direction_edit(string direction)

    {
      int index = 9999999;

      for (int i=0; i<exitkeys.Count; i++)
        if (direction == (string)exitkeys[i])
          index = i;

      if (index != 9999999)

      {
        string key  = ((string)exitkeys[index]);
        string desc = ((string)exitdescs[index]);

        look_editor.set_look(key, desc, 1);
        look_editor.lock_key();
        look_editor.ShowDialog();
        look_editor.unlock_key();

        if (look_editor.get_saved() == true)

        {
          exitkeys.Remove(key);
          exitdescs.Remove(desc);
          exitkeys.Add(look_editor.get_keyword());
          exitdescs.Add(look_editor.get_desc());
        }
      }

      else

      {
        string key = direction;
        string desc = "";

        exitkeys.Add(key);
        exitdescs.Add(desc);
        direction_edit(direction);
      }
    }

    private void remove_exitlook(string str)

    {
      int index = 9999999;

      for (int i=0; i<exitkeys.Count; i++)
        if (str == (string)exitkeys[i])
          index = i;

      if (index != 9999999)

      {
        exitkeys.Remove(str);
        exitdescs.Remove((string)exitdescs[index]);
      }
    }

    private bool validate()

    {
      for (int i=0; i<txtID.Text.Length; i++)
        if ((txtID.Text[i] < '0') || (txtID.Text[i] > '9'))

        {
          MessageBox.Show("Room ID must contain only numerical values.", "Invalid Room ID", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
          return false;
        }

      if (chkNorth.Checked)
      for (int i=0; i<txtNorth.Text.Length; i++)
        if ((txtNorth.Text[i] < '0') || (txtNorth.Text[i] > '9'))

        {
          MessageBox.Show("North room ID must contain only numerical values.", "Invalid North ID", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
          return false;
        }

      if (chkSouth.Checked)
      for (int i=0; i<txtSouth.Text.Length; i++)
        if ((txtSouth.Text[i] < '0') || (txtSouth.Text[i] > '9'))

        {
          MessageBox.Show("South room ID must contain only numerical values.", "Invalid North ID", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
          return false;
        }

      if (chkEast.Checked)
      for (int i=0; i<txtEast.Text.Length; i++)
        if ((txtEast.Text[i] < '0') || (txtEast.Text[i] > '9'))

        {
          MessageBox.Show("East room ID must contain only numerical values.", "Invalid North ID", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
          return false;
        }

      if (chkWest.Checked)
      for (int i=0; i<txtWest.Text.Length; i++)
        if ((txtWest.Text[i] < '0') || (txtWest.Text[i] > '9'))

        {
          MessageBox.Show("West room ID must contain only numerical values.", "Invalid North ID", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
          return false;
        }

      if (chkUp.Checked)
      for (int i=0; i<txtUp.Text.Length; i++)
        if ((txtUp.Text[i] < '0') || (txtUp.Text[i] > '9'))

        {
          MessageBox.Show("Up room ID must contain only numerical values.", "Invalid North ID", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
          return false;
        }

      if (chkDown.Checked)
      for (int i=0; i<txtDown.Text.Length; i++)
        if ((txtDown.Text[i] < '0') || (txtDown.Text[i] > '9'))

        {
          MessageBox.Show("Down room ID must contain only numerical values.", "Invalid North ID", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
          return false;
        }

      if (txtID.Text == "")

      {
        MessageBox.Show("You must enter a room ID.", "Missing ID", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        return false;
      }

      if (txtName.Text == "")

      {
        MessageBox.Show("You must enter a room name.", "Missing Name", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
        return false;
      }

      int n = Int32.Parse(frmMain.get_zone_id());
      int m = (room_list.get_size() / 100);
      int min_id = n * 100;
      int max_id = min_id + 99 + (100*m);
      int id_num = Int32.Parse(txtID.Text);

      if ((id_num < min_id) || (id_num > max_id))

      {
        if (MessageBox.Show ("Room ID " + id_num.ToString() + " does not fall within this Zone's Range. (" +
        min_id.ToString() + "-" + max_id.ToString() + ").  Do you want to save anyway?", "Room ID out of bounds.",
        MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
          return true;

        return false;
      }

      return true;
    }

    private void update_ilist()

    {
      string temp;

      lstItem.Items.Clear();

      for (int i=0; i<ilist.get_size(); i++)

      {
        temp  = ilist.get_item(i).id;
        temp  = "[" + temp.PadLeft(5, '0') + "] ";
        temp += ilist.get_item(i).name;
        lstItem.Items.Add(temp);
      }
    }

    private void update_moblist()

    {
      string temp;

      lstMob.Items.Clear();

      for (int i=0; i<mlist.get_size(); i++)

      {
        temp  = mlist.get_mob(i).id;
        temp  = "[" + temp.PadLeft(5, '0') + "] ";
        temp += mlist.get_mob(i).name;
        lstMob.Items.Add(temp);
      }
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


    private void cmdCancel_Click(object sender, System.EventArgs e)

    {
      if (changed)

      {
        if (MessageBox.Show ("Do you want to save your changes?", "Room Cancel",
          MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.No)

        {
          active = false;
          this.Hide();
        }

        else
          cmdSave_Click(sender, e);
      }

      else

      {
        active = false;
        this.Hide();
      }
    }

    private void cmdSave_Click(object sender, System.EventArgs e)

    {
      if (validate())
      if (changed)

      {
          if (original_id == txtID.Text)
            if (room_list.find_room(txtID.Text) == null)

            {
              save_room();
              room_list.add_room(active_room);
              active = false;
              this.Hide();
            }

            else

            {
              room_list.remove_room(room_list.find_room(txtID.Text));
              save_room();
              room_list.add_room(active_room);
              active = false;
              this.Hide();
            }

          else
            if (room_list.find_room(txtID.Text) == null)

          {
            if (original_id != "")
              if (room_list.find_room(original_id) != null)
                room_list.remove_room(room_list.find_room(original_id));

            save_room();
            room_list.add_room(active_room);
            active = false;
            this.Hide();
          }

          else
            if (MessageBox.Show ("Room ID changed. This will overwrite the existing room with the id of " + txtID.Text, "Room Replacement Warning",
            MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)

          {
            if (room_list.find_room(original_id) != null)
              room_list.remove_room(room_list.find_room(original_id));

            if (room_list.find_room(txtID.Text) != null)
              room_list.remove_room(room_list.find_room(txtID.Text));

            save_room();
            room_list.add_room(active_room);
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

    private void chkNorth_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
      if (chkNorth.Checked)
        txtNorth.ReadOnly = false;
      else

      {
        txtNorth.ReadOnly = true;
        txtNorth.Text = "";
        remove_exitlook("north");
      }
    }

    private void chkSouth_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
      if (chkSouth.Checked)
        txtSouth.ReadOnly = false;
      else

      {
        txtSouth.ReadOnly = true;
        txtSouth.Text = "";
        remove_exitlook("south");
      }
    }

    private void chkEast_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
      if (chkEast.Checked)
        txtEast.ReadOnly = false;
      else

      {
        txtEast.ReadOnly = true;
        txtEast.Text = "";
        remove_exitlook("east");
      }
    }

    private void chkWest_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
      if (chkWest.Checked)
        txtWest.ReadOnly = false;
      else

      {
        txtWest.ReadOnly = true;
        txtWest.Text = "";
        remove_exitlook("west");
      }
    }

    private void chkUp_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
      if (chkUp.Checked)
        txtUp.ReadOnly = false;
      else

      {
        txtUp.ReadOnly = true;
        txtUp.Text = "";
        remove_exitlook("up");
      }
    }

    private void chkDown_CheckedChanged(object sender, System.EventArgs e)

    {
      if (active)
      if (chkDown.Checked)
        txtDown.ReadOnly = false;
      else

      {
        txtDown.ReadOnly = true;
        txtDown.Text = "";
        remove_exitlook("down");
      }
    }

    private void cmdAddLook_Click(object sender, System.EventArgs e)

    {
      look_editor.set_look("", "", 2);
      look_editor.ShowDialog();

      if (look_editor.get_saved() == true)

      {
        lstLook.Items.Add(Utility.clear_whitespace(look_editor.get_keyword()));
        keys.Add(Utility.clear_whitespace(look_editor.get_keyword()));
        descs.Add(Utility.clear_whitespace(look_editor.get_desc()));
      }
    }

    private void cmdEditLook_Click(object sender, System.EventArgs e)

    {
      if (lstLook.SelectedItem != null)

      {
        int index = 0;
        string key, desc;

        for (int i=0; i<keys.Count; i++)
          if ((string)lstLook.SelectedItem == (string)keys[i])
            index = i;

        key   = ((string)keys[index]);
        desc  = ((string)descs[index]);

        look_editor.set_look(key, desc, 2);
        look_editor.ShowDialog();

        if (look_editor.get_saved() == true)

        {
          keys.Remove(key);
          descs.Remove(desc);
          keys.Add(look_editor.get_keyword());
          descs.Add(look_editor.get_desc());

          lstLook.Items.Remove(key);
          lstLook.Items.Add(look_editor.get_keyword());
        }
      }
    }

    private void cmdRemoveLook_Click(object sender, System.EventArgs e)

    {
      int index = 0;
      string key, desc;

      if (lstLook.SelectedItem != null)

      {
        for (int i=0; i<keys.Count; i++)
          if ((string)lstLook.SelectedItem == (string)keys[i])
            index = i;

        key   = ((string)keys[index]);
        desc  = ((string)descs[index]);

        keys.Remove(key);
        descs.Remove(desc);
        lstLook.Items.Remove(key);
      }
    }

    private void cmdNorthLook_Click(object sender, System.EventArgs e)

    {
      if (chkNorth.Checked)
        direction_edit("north");
    }

    private void cmdSouthLook_Click(object sender, System.EventArgs e)

    {
      if (chkSouth.Checked)
        direction_edit("south");
    }

    private void cmdEastLook_Click(object sender, System.EventArgs e)

    {
      if (chkEast.Checked)
        direction_edit("east");
    }

    private void cmdWestLook_Click(object sender, System.EventArgs e)

    {
      if (chkWest.Checked)
        direction_edit("west");
    }

    private void cmdUpLook_Click(object sender, System.EventArgs e)

    {
      if (chkUp.Checked)
        direction_edit("up");
    }

    private void cmdDownLook_Click(object sender, System.EventArgs e)

    {
      if (chkDown.Checked)
        direction_edit("down");
    }
    protected override void Dispose( bool disposing )

    {
      if( disposing )
        if(components != null)
          components.Dispose();

      base.Dispose( disposing );
    }

    private void cmdAddItem_Click(object sender, System.EventArgs e)

    {
      item_picker.disable_freq();
      item_picker.ShowDialog();
      if (item_picker.get_saved())
        ilist.add_item(item_picker.get_selected());
      item_picker.enable_freq();

      update_ilist();
    }

    private void cmdRemoveItem_Click(object sender, System.EventArgs e)

    {
      if (lstItem.SelectedItem != null)

      {
        ilist.remove_item(lstItem.SelectedIndex);
        update_ilist();
      }
    }

    private void cmdAddAction_Click(object sender, System.EventArgs e)

    {
      if ((txtAction.Text != "") && (txtActionFreq.Text != ""))

      {
        int i = -1;

        try

        {
          i = Int32.Parse(txtActionFreq.Text);

          if (i < 0 || i > 60000)
            throw new System.Exception();
        }

        catch (System.FormatException)

        {
          MessageBox.Show("Invalid frequency setting.");
          return;
        }

        catch (System.Exception)

        {
          MessageBox.Show("Frequency out of bounds.  Must be between 0-60,000");
          return;
        }

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

    private void cmdAddMob_Click(object sender, System.EventArgs e)
    {
      mob_picker.ShowDialog();
      if (mob_picker.get_saved())
        mlist.add_mob(mob_picker.get_selected());

      update_moblist();
    }

    private void cmdRemoveMob_Click(object sender, System.EventArgs e)
    {
      if (lstMob.SelectedItem != null)

      {
        mlist.remove_mob(lstMob.SelectedIndex);
        update_moblist();
      }
    }

    private void frmRoomEditor_Click(object sender, EventArgs e)

    {
      if (watch_change)
        changed = true;
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

    private void create_link(object sender, System.EventArgs e)

    {
      create_link(sender, true);
    }

    private void create_link(object sender, bool audible)

    {
      if (sender == cmdLinkNorth)

      {
        if (!chkNorth.Checked)
          return;

        room temp_room = room_list.find_room(txtNorth.Text);

        if (temp_room == null) {
          if (audible) MessageBox.Show("Cannot create link.  Room does not exist.");
          return; }

        temp_room.set_exit(1, txtID.Text);
        if (audible) MessageBox.Show("Link created to room " + txtNorth.Text + ".");
      }

      else if (sender == cmdLinkSouth)

      {
        if (!chkSouth.Checked)
          return;

        room temp_room = room_list.find_room(txtSouth.Text);

        if (temp_room == null) {
          if (audible) MessageBox.Show("Cannot link.  Room does not exist.");
          return; }

        temp_room.set_exit(0, txtID.Text);
        if (audible) MessageBox.Show("Link created to room " + txtSouth.Text + ".");
      }

      else if (sender == cmdLinkEast)

      {
        if (!chkEast.Checked)
          return;

        room temp_room = room_list.find_room(txtEast.Text);

        if (temp_room == null) {
          if (audible) MessageBox.Show("Cannot link.  Room does not exist.");
          return; }

        temp_room.set_exit(3, txtID.Text);
        if (audible) MessageBox.Show("Link created to room " + txtEast.Text + ".");
      }

      else if (sender == cmdLinkWest)

      {
        if (!chkWest.Checked)
          return;

        room temp_room = room_list.find_room(txtWest.Text);

        if (temp_room == null) {
          if (audible) MessageBox.Show("Cannot link.  Room does not exist.");
          return; }

        temp_room.set_exit(2, txtID.Text);
        if (audible) MessageBox.Show("Link created to room " + txtWest.Text + ".");
      }

      else if (sender == cmdLinkUp)

      {
        if (!chkUp.Checked)
          return;

        room temp_room = room_list.find_room(txtUp.Text);

        if (temp_room == null) {
          if (audible) MessageBox.Show("Cannot link.  Room does not exist.");
          return; }

        temp_room.set_exit(5, txtID.Text);
        if (audible) MessageBox.Show("Link created to room " + txtUp.Text + ".");
      }

      else if (sender == cmdLinkDown)

      {
        if (!chkDown.Checked)
          return;

        room temp_room = room_list.find_room(txtDown.Text);

        if (temp_room == null)  {
          if (audible) MessageBox.Show("Cannot link.  Room does not exist.");
          return; }

        temp_room.set_exit(4, txtID.Text);
        if (audible) MessageBox.Show("Link created to room " + txtDown.Text + ".");
      }
    }

    private void reverse_link(object sender, System.EventArgs e)

    {
      for (int i=0; i<room_list.get_size(); i++)

      {
        room temp_room = room_list.get_room(i);

        if (temp_room.get_exit(0) == txtID.Text) {
          chkSouth.Checked = true;
          txtSouth.Text = temp_room.get_id(); }

        if (temp_room.get_exit(1) == txtID.Text) {
          chkNorth.Checked = true;
          txtNorth.Text = temp_room.get_id(); }

        if (temp_room.get_exit(2) == txtID.Text) {
          chkWest.Checked = true;
          txtWest.Text = temp_room.get_id(); }

        if (temp_room.get_exit(3) == txtID.Text) {
          chkEast.Checked = true;
          txtEast.Text = temp_room.get_id(); }

        if (temp_room.get_exit(4) == txtID.Text) {
          chkDown.Checked = true;
          txtDown.Text = temp_room.get_id(); }

        if (temp_room.get_exit(5) == txtID.Text) {
          chkUp.Checked = true;
          txtUp.Text = temp_room.get_id(); }
      }
    }

    private void all_link(object sender, System.EventArgs e)

    {
      create_link(cmdLinkNorth, false);
      create_link(cmdLinkSouth, false);
      create_link(cmdLinkEast, false);
      create_link(cmdLinkWest, false);
      create_link(cmdLinkUp, false);
      create_link(cmdLinkDown, false);

      MessageBox.Show("All possible links created.");
    }
    
    private void cmdDoubleLink_Click(object sender, System.EventArgs e)
    
    {
      all_link(sender, e);
      reverse_link(sender, e);
    }

    private void chkExact_CheckedChanged(object sender, System.EventArgs e)
    
    {
      if (!active) return;
      
      if (chkExact.Checked)
      
      {
        if (MessageBox.Show("Going to exact mode will erase your current description.  Continue?",
          "Exact Mode", MessageBoxButtons.OKCancel) == DialogResult.Cancel)
          return;
        
        txtDesc.ReadOnly = true;
        txtDesc.Text = "EXACT DESCRIPTION";
        txtDesc.BackColor = Color.FromName("Control");
      }
      
      else
      
      {
        txtDesc.ReadOnly = false;
        txtDesc.Text = "";
        txtDesc.BackColor = Color.FromName("Window");
        exact_holder = "";
      }
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
  }
}
