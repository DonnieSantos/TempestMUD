using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Data;
using System.IO;

namespace Tempest_Builder

{
  public class frmMain : System.Windows.Forms.Form

  {
    private frmRoomEditor room_editor;
    private frmItemEditor item_editor;
    private frmItemPicker item_picker;
    private frmMobPicker mob_picker;
    private frmMobEditor mob_editor;
    private roomlist room_list;
    private itemlist item_list;
    private moblist mob_list;
    public static frmMain main_pointer;

    private string item_path;
    private string mob_path;
    private string zone_path;

    private bool saved_before;
    private bool main_changed;

    private Button cmdAddItem, cmdEditItem, cmdDeleteItem;
    private Button cmdAddRoom, cmdEditRoom, cmdDeleteRoom;
    private Button cmdAddMob, cmdEditMob, cmdDeleteMob;
    private Label label8, label3, label4;
    private Label label5, label1, label2, label6, label7;
    private TextBox txtZoneID, txtZoneName, txtRepop;
    private ListBox lstRoom, lstItem, lstMob;
    private MenuItem mnuFile;
    private MenuItem mnuNew;
    private MenuItem mnuOpen;
    private MenuItem mnuSave;
    private MenuItem mnuExit;
    private MenuItem mnuEdit;
    private MenuItem mnuCopy;
    private MenuItem mnuCut;
    private MenuItem mnuPaste;
    private MenuItem mnuHelp;
    private MenuItem mnuAbout;
    private MenuItem mnuSaveAs;
    private MenuItem menuItem1;
    private MainMenu mainMenu;
    private ComboBox cboScheme;
    private System.Windows.Forms.Label label10;
    public System.Windows.Forms.ComboBox cboZoneTerrain;
    private System.Windows.Forms.Button cmdLinkZone;
    private System.Windows.Forms.Button cmdUpdateTerrain;
    private System.Windows.Forms.Button cmdDebugExits;
    private System.Windows.Forms.MenuItem menuItem2;
    private System.Windows.Forms.MenuItem menuItem3;
    private System.Windows.Forms.MenuItem menuItem4;
    private System.Windows.Forms.MenuItem menuItem5;
    private System.Windows.Forms.MenuItem menuItem6;
    private System.Windows.Forms.MenuItem menuItem7;
    private System.Windows.Forms.MenuItem menuItem8;
    private System.Windows.Forms.MenuItem menuItem9;
    private System.Windows.Forms.MenuItem menuItem10;
    private System.Windows.Forms.MenuItem menuItem11;
    private System.Windows.Forms.MenuItem menuItem12;
    private System.Windows.Forms.MenuItem menuItem13;
    private System.Windows.Forms.MenuItem menuItem14;
    private System.Windows.Forms.MenuItem menuItem15;
    private System.Windows.Forms.MenuItem menuItem16;
    private System.Windows.Forms.MenuItem menuItem17;
    private System.Windows.Forms.MenuItem menuItem18;
    private System.Windows.Forms.MenuItem menuItem19;
    private System.ComponentModel.Container components = null;

    public frmMain()

    {
      InitializeComponent();
      room_list   = new roomlist();
      item_list   = new itemlist();
      mob_list    = new moblist();

      item_picker = new frmItemPicker(item_list);
      mob_picker  = new frmMobPicker(mob_list);

      frmMain.main_pointer = this;
      saved_before = false;
      main_changed = false;
      
      cboScheme.Click += new EventHandler(form_changed);
      txtZoneID.TextChanged += new EventHandler(form_changed); 
      txtZoneName.TextChanged += new EventHandler(form_changed); 
      txtRepop.TextChanged += new EventHandler(form_changed);
      cboZoneTerrain.Click += new EventHandler(form_changed);      
      
      clear();
    }

    public static string get_zone_id() { return main_pointer.txtZoneID.Text; }

    protected override void Dispose( bool disposing )

    {
      if( disposing )
        if (components != null)
          components.Dispose();

      base.Dispose( disposing );
    }

    #region Windows Form Designer generated code

    private void InitializeComponent()
    {
      System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(frmMain));
      this.label1 = new System.Windows.Forms.Label();
      this.mainMenu = new System.Windows.Forms.MainMenu();
      this.mnuFile = new System.Windows.Forms.MenuItem();
      this.mnuNew = new System.Windows.Forms.MenuItem();
      this.mnuOpen = new System.Windows.Forms.MenuItem();
      this.mnuSave = new System.Windows.Forms.MenuItem();
      this.mnuSaveAs = new System.Windows.Forms.MenuItem();
      this.menuItem1 = new System.Windows.Forms.MenuItem();
      this.mnuExit = new System.Windows.Forms.MenuItem();
      this.mnuEdit = new System.Windows.Forms.MenuItem();
      this.mnuCopy = new System.Windows.Forms.MenuItem();
      this.mnuCut = new System.Windows.Forms.MenuItem();
      this.mnuPaste = new System.Windows.Forms.MenuItem();
      this.mnuHelp = new System.Windows.Forms.MenuItem();
      this.mnuAbout = new System.Windows.Forms.MenuItem();
      this.menuItem2 = new System.Windows.Forms.MenuItem();
      this.menuItem3 = new System.Windows.Forms.MenuItem();
      this.menuItem4 = new System.Windows.Forms.MenuItem();
      this.menuItem5 = new System.Windows.Forms.MenuItem();
      this.menuItem6 = new System.Windows.Forms.MenuItem();
      this.menuItem15 = new System.Windows.Forms.MenuItem();
      this.menuItem16 = new System.Windows.Forms.MenuItem();
      this.menuItem17 = new System.Windows.Forms.MenuItem();
      this.menuItem7 = new System.Windows.Forms.MenuItem();
      this.menuItem8 = new System.Windows.Forms.MenuItem();
      this.menuItem9 = new System.Windows.Forms.MenuItem();
      this.menuItem10 = new System.Windows.Forms.MenuItem();
      this.menuItem11 = new System.Windows.Forms.MenuItem();
      this.menuItem12 = new System.Windows.Forms.MenuItem();
      this.menuItem13 = new System.Windows.Forms.MenuItem();
      this.menuItem18 = new System.Windows.Forms.MenuItem();
      this.menuItem19 = new System.Windows.Forms.MenuItem();
      this.menuItem14 = new System.Windows.Forms.MenuItem();
      this.txtZoneID = new System.Windows.Forms.TextBox();
      this.label2 = new System.Windows.Forms.Label();
      this.txtZoneName = new System.Windows.Forms.TextBox();
      this.label3 = new System.Windows.Forms.Label();
      this.label4 = new System.Windows.Forms.Label();
      this.label5 = new System.Windows.Forms.Label();
      this.lstRoom = new System.Windows.Forms.ListBox();
      this.cmdAddRoom = new System.Windows.Forms.Button();
      this.cmdEditRoom = new System.Windows.Forms.Button();
      this.cmdDeleteRoom = new System.Windows.Forms.Button();
      this.lstItem = new System.Windows.Forms.ListBox();
      this.label6 = new System.Windows.Forms.Label();
      this.cmdAddItem = new System.Windows.Forms.Button();
      this.cmdEditItem = new System.Windows.Forms.Button();
      this.cmdDeleteItem = new System.Windows.Forms.Button();
      this.lstMob = new System.Windows.Forms.ListBox();
      this.label7 = new System.Windows.Forms.Label();
      this.cmdAddMob = new System.Windows.Forms.Button();
      this.cmdEditMob = new System.Windows.Forms.Button();
      this.cmdDeleteMob = new System.Windows.Forms.Button();
      this.txtRepop = new System.Windows.Forms.TextBox();
      this.label8 = new System.Windows.Forms.Label();
      this.cboScheme = new System.Windows.Forms.ComboBox();
      this.label10 = new System.Windows.Forms.Label();
      this.cboZoneTerrain = new System.Windows.Forms.ComboBox();
      this.cmdLinkZone = new System.Windows.Forms.Button();
      this.cmdUpdateTerrain = new System.Windows.Forms.Button();
      this.cmdDebugExits = new System.Windows.Forms.Button();
      this.SuspendLayout();
      // 
      // label1
      // 
      this.label1.BackColor = System.Drawing.Color.Lavender;
      this.label1.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label1.Font = new System.Drawing.Font("Courier New", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label1.ForeColor = System.Drawing.SystemColors.ControlText;
      this.label1.ImageAlign = System.Drawing.ContentAlignment.BottomCenter;
      this.label1.Location = new System.Drawing.Point(8, 8);
      this.label1.Name = "label1";
      this.label1.Size = new System.Drawing.Size(248, 16);
      this.label1.TabIndex = 0;
      this.label1.Text = "Zone Information";
      this.label1.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
      // 
      // mainMenu
      // 
      this.mainMenu.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
                                                                             this.mnuFile,
                                                                             this.mnuEdit,
                                                                             this.mnuHelp});
      // 
      // mnuFile
      // 
      this.mnuFile.Index = 0;
      this.mnuFile.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
                                                                            this.mnuNew,
                                                                            this.mnuOpen,
                                                                            this.mnuSave,
                                                                            this.mnuSaveAs,
                                                                            this.menuItem1,
                                                                            this.mnuExit});
      this.mnuFile.Text = "File";
      // 
      // mnuNew
      // 
      this.mnuNew.Index = 0;
      this.mnuNew.Text = "New";
      this.mnuNew.Click += new System.EventHandler(this.mnuNew_Click);
      // 
      // mnuOpen
      // 
      this.mnuOpen.Index = 1;
      this.mnuOpen.Text = "Open";
      this.mnuOpen.Click += new System.EventHandler(this.mnuOpen_Click);
      // 
      // mnuSave
      // 
      this.mnuSave.Index = 2;
      this.mnuSave.Text = "Save";
      this.mnuSave.Click += new System.EventHandler(this.mnuSave_Click);
      // 
      // mnuSaveAs
      // 
      this.mnuSaveAs.Index = 3;
      this.mnuSaveAs.Text = "Save As";
      this.mnuSaveAs.Click += new System.EventHandler(this.mnuSaveAs_Click);
      // 
      // menuItem1
      // 
      this.menuItem1.Index = 4;
      this.menuItem1.Text = "-";
      // 
      // mnuExit
      // 
      this.mnuExit.Index = 5;
      this.mnuExit.Text = "Exit";
      this.mnuExit.Click += new System.EventHandler(this.mnuExit_Click);
      // 
      // mnuEdit
      // 
      this.mnuEdit.Index = 1;
      this.mnuEdit.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
                                                                            this.mnuCopy,
                                                                            this.mnuCut,
                                                                            this.mnuPaste});
      this.mnuEdit.Text = "Edit";
      // 
      // mnuCopy
      // 
      this.mnuCopy.Index = 0;
      this.mnuCopy.Text = "Copy";
      this.mnuCopy.Click += new System.EventHandler(this.mnuCopy_Click);
      // 
      // mnuCut
      // 
      this.mnuCut.Index = 1;
      this.mnuCut.Text = "Cut";
      this.mnuCut.Click += new System.EventHandler(this.mnuCut_Click);
      // 
      // mnuPaste
      // 
      this.mnuPaste.Index = 2;
      this.mnuPaste.Text = "Paste";
      this.mnuPaste.Click += new System.EventHandler(this.mnuPaste_Click);
      // 
      // mnuHelp
      // 
      this.mnuHelp.Index = 2;
      this.mnuHelp.MenuItems.AddRange(new System.Windows.Forms.MenuItem[] {
                                                                            this.mnuAbout,
                                                                            this.menuItem2,
                                                                            this.menuItem3,
                                                                            this.menuItem4,
                                                                            this.menuItem5,
                                                                            this.menuItem6,
                                                                            this.menuItem15,
                                                                            this.menuItem16,
                                                                            this.menuItem17,
                                                                            this.menuItem7,
                                                                            this.menuItem8,
                                                                            this.menuItem9,
                                                                            this.menuItem10,
                                                                            this.menuItem11,
                                                                            this.menuItem12,
                                                                            this.menuItem13,
                                                                            this.menuItem18,
                                                                            this.menuItem19,
                                                                            this.menuItem14});
      this.mnuHelp.Text = "Help";
      // 
      // mnuAbout
      // 
      this.mnuAbout.Index = 0;
      this.mnuAbout.Text = "Zone Form";
      this.mnuAbout.Click += new System.EventHandler(this.mnuAbout_Click);
      // 
      // menuItem2
      // 
      this.menuItem2.Index = 1;
      this.menuItem2.Text = "-";
      // 
      // menuItem3
      // 
      this.menuItem3.Index = 2;
      this.menuItem3.Text = "Room Form";
      this.menuItem3.Click += new System.EventHandler(this.menuItem3_Click);
      // 
      // menuItem4
      // 
      this.menuItem4.Index = 3;
      this.menuItem4.Text = "Mobile Form";
      this.menuItem4.Click += new System.EventHandler(this.menuItem4_Click);
      // 
      // menuItem5
      // 
      this.menuItem5.Index = 4;
      this.menuItem5.Text = "Item Form";
      this.menuItem5.Click += new System.EventHandler(this.menuItem5_Click);
      // 
      // menuItem6
      // 
      this.menuItem6.Index = 5;
      this.menuItem6.Text = "-";
      // 
      // menuItem15
      // 
      this.menuItem15.Index = 6;
      this.menuItem15.Text = "Colors";
      this.menuItem15.Click += new System.EventHandler(this.menuItem15_Click);
      // 
      // menuItem16
      // 
      this.menuItem16.Index = 7;
      this.menuItem16.Text = "Frequencies";
      this.menuItem16.Click += new System.EventHandler(this.menuItem16_Click);
      // 
      // menuItem17
      // 
      this.menuItem17.Index = 8;
      this.menuItem17.Text = "-";
      // 
      // menuItem7
      // 
      this.menuItem7.Index = 9;
      this.menuItem7.Text = "Balancing Mobile Stats";
      this.menuItem7.Click += new System.EventHandler(this.menuItem7_Click);
      // 
      // menuItem8
      // 
      this.menuItem8.Index = 10;
      this.menuItem8.Text = "Balancing Item Stats";
      this.menuItem8.Click += new System.EventHandler(this.menuItem8_Click);
      // 
      // menuItem9
      // 
      this.menuItem9.Index = 11;
      this.menuItem9.Text = "Balancing Item Prices";
      this.menuItem9.Click += new System.EventHandler(this.menuItem9_Click);
      // 
      // menuItem10
      // 
      this.menuItem10.Index = 12;
      this.menuItem10.Text = "-";
      // 
      // menuItem11
      // 
      this.menuItem11.Index = 13;
      this.menuItem11.Text = "Standard Weapons List";
      this.menuItem11.Click += new System.EventHandler(this.menuItem11_Click);
      // 
      // menuItem12
      // 
      this.menuItem12.Index = 14;
      this.menuItem12.Text = "Standard Armor List";
      this.menuItem12.Click += new System.EventHandler(this.menuItem12_Click);
      // 
      // menuItem13
      // 
      this.menuItem13.Index = 15;
      this.menuItem13.Text = "-";
      // 
      // menuItem18
      // 
      this.menuItem18.Index = 16;
      this.menuItem18.Text = "Writing And Theme";
      this.menuItem18.Click += new System.EventHandler(this.menuItem18_Click);
      // 
      // menuItem19
      // 
      this.menuItem19.Index = 17;
      this.menuItem19.Text = "-";
      // 
      // menuItem14
      // 
      this.menuItem14.Index = 18;
      this.menuItem14.Text = "About";
      this.menuItem14.Click += new System.EventHandler(this.menuItem14_Click);
      // 
      // txtZoneID
      // 
      this.txtZoneID.BackColor = System.Drawing.SystemColors.HighlightText;
      this.txtZoneID.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtZoneID.ForeColor = System.Drawing.SystemColors.InfoText;
      this.txtZoneID.Location = new System.Drawing.Point(80, 32);
      this.txtZoneID.Name = "txtZoneID";
      this.txtZoneID.Size = new System.Drawing.Size(72, 20);
      this.txtZoneID.TabIndex = 0;
      this.txtZoneID.Text = "";
      // 
      // label2
      // 
      this.label2.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label2.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label2.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label2.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label2.ForeColor = System.Drawing.SystemColors.ControlText;
      this.label2.Location = new System.Drawing.Point(8, 32);
      this.label2.Name = "label2";
      this.label2.Size = new System.Drawing.Size(64, 16);
      this.label2.TabIndex = 2;
      this.label2.Text = "ID:";
      this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtZoneName
      // 
      this.txtZoneName.BackColor = System.Drawing.SystemColors.HighlightText;
      this.txtZoneName.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtZoneName.ForeColor = System.Drawing.SystemColors.InfoText;
      this.txtZoneName.Location = new System.Drawing.Point(80, 56);
      this.txtZoneName.Name = "txtZoneName";
      this.txtZoneName.Size = new System.Drawing.Size(176, 20);
      this.txtZoneName.TabIndex = 1;
      this.txtZoneName.Text = "";
      // 
      // label3
      // 
      this.label3.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label3.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label3.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label3.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label3.ForeColor = System.Drawing.SystemColors.ControlText;
      this.label3.Location = new System.Drawing.Point(8, 56);
      this.label3.Name = "label3";
      this.label3.Size = new System.Drawing.Size(64, 16);
      this.label3.TabIndex = 4;
      this.label3.Text = "Name:";
      this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label4
      // 
      this.label4.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label4.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label4.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label4.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label4.ForeColor = System.Drawing.SystemColors.ControlText;
      this.label4.Location = new System.Drawing.Point(8, 104);
      this.label4.Name = "label4";
      this.label4.Size = new System.Drawing.Size(64, 16);
      this.label4.TabIndex = 6;
      this.label4.Text = "Scheme:";
      this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // label5
      // 
      this.label5.BackColor = System.Drawing.Color.Lavender;
      this.label5.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label5.Font = new System.Drawing.Font("Courier New", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label5.ForeColor = System.Drawing.SystemColors.ControlText;
      this.label5.Location = new System.Drawing.Point(272, 8);
      this.label5.Name = "label5";
      this.label5.Size = new System.Drawing.Size(251, 16);
      this.label5.TabIndex = 7;
      this.label5.Text = "Room List";
      this.label5.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
      // 
      // lstRoom
      // 
      this.lstRoom.BackColor = System.Drawing.SystemColors.Menu;
      this.lstRoom.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstRoom.ForeColor = System.Drawing.SystemColors.ControlText;
      this.lstRoom.Location = new System.Drawing.Point(272, 32);
      this.lstRoom.Name = "lstRoom";
      this.lstRoom.Size = new System.Drawing.Size(251, 197);
      this.lstRoom.TabIndex = 4;
      this.lstRoom.DoubleClick += new System.EventHandler(this.lstRoom_DoubleClick);
      // 
      // cmdAddRoom
      // 
      this.cmdAddRoom.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddRoom.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddRoom.ForeColor = System.Drawing.SystemColors.InfoText;
      this.cmdAddRoom.Location = new System.Drawing.Point(272, 232);
      this.cmdAddRoom.Name = "cmdAddRoom";
      this.cmdAddRoom.TabIndex = 5;
      this.cmdAddRoom.Text = "Add";
      this.cmdAddRoom.Click += new System.EventHandler(this.cmdAddRoom_Click);
      // 
      // cmdEditRoom
      // 
      this.cmdEditRoom.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdEditRoom.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdEditRoom.Location = new System.Drawing.Point(360, 232);
      this.cmdEditRoom.Name = "cmdEditRoom";
      this.cmdEditRoom.TabIndex = 6;
      this.cmdEditRoom.Text = "Edit";
      this.cmdEditRoom.Click += new System.EventHandler(this.cmdEditRoom_Click);
      // 
      // cmdDeleteRoom
      // 
      this.cmdDeleteRoom.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdDeleteRoom.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdDeleteRoom.Location = new System.Drawing.Point(448, 232);
      this.cmdDeleteRoom.Name = "cmdDeleteRoom";
      this.cmdDeleteRoom.TabIndex = 7;
      this.cmdDeleteRoom.Text = "Delete";
      this.cmdDeleteRoom.Click += new System.EventHandler(this.cmdDeleteRoom_Click);
      // 
      // lstItem
      // 
      this.lstItem.BackColor = System.Drawing.SystemColors.Menu;
      this.lstItem.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstItem.ForeColor = System.Drawing.SystemColors.MenuText;
      this.lstItem.Location = new System.Drawing.Point(8, 288);
      this.lstItem.Name = "lstItem";
      this.lstItem.Size = new System.Drawing.Size(251, 197);
      this.lstItem.TabIndex = 8;
      this.lstItem.DoubleClick += new System.EventHandler(this.lstItem_DoubleClick);
      // 
      // label6
      // 
      this.label6.BackColor = System.Drawing.Color.Lavender;
      this.label6.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label6.Font = new System.Drawing.Font("Courier New", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label6.ForeColor = System.Drawing.SystemColors.MenuText;
      this.label6.Location = new System.Drawing.Point(8, 264);
      this.label6.Name = "label6";
      this.label6.Size = new System.Drawing.Size(251, 16);
      this.label6.TabIndex = 13;
      this.label6.Text = "Item List";
      this.label6.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
      // 
      // cmdAddItem
      // 
      this.cmdAddItem.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddItem.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddItem.Location = new System.Drawing.Point(8, 488);
      this.cmdAddItem.Name = "cmdAddItem";
      this.cmdAddItem.TabIndex = 9;
      this.cmdAddItem.Text = "Add";
      this.cmdAddItem.Click += new System.EventHandler(this.cmdAddItem_Click);
      // 
      // cmdEditItem
      // 
      this.cmdEditItem.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdEditItem.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdEditItem.Location = new System.Drawing.Point(96, 488);
      this.cmdEditItem.Name = "cmdEditItem";
      this.cmdEditItem.TabIndex = 10;
      this.cmdEditItem.Text = "Edit";
      this.cmdEditItem.Click += new System.EventHandler(this.cmdEditItem_Click);
      // 
      // cmdDeleteItem
      // 
      this.cmdDeleteItem.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdDeleteItem.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdDeleteItem.Location = new System.Drawing.Point(184, 488);
      this.cmdDeleteItem.Name = "cmdDeleteItem";
      this.cmdDeleteItem.TabIndex = 11;
      this.cmdDeleteItem.Text = "Delete";
      this.cmdDeleteItem.Click += new System.EventHandler(this.cmdDeleteItem_Click);
      // 
      // lstMob
      // 
      this.lstMob.BackColor = System.Drawing.SystemColors.Menu;
      this.lstMob.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.lstMob.ForeColor = System.Drawing.SystemColors.MenuText;
      this.lstMob.Location = new System.Drawing.Point(272, 288);
      this.lstMob.Name = "lstMob";
      this.lstMob.Size = new System.Drawing.Size(251, 197);
      this.lstMob.TabIndex = 12;
      this.lstMob.DoubleClick += new System.EventHandler(this.lstMob_DoubleClick);
      // 
      // label7
      // 
      this.label7.BackColor = System.Drawing.Color.Lavender;
      this.label7.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label7.Font = new System.Drawing.Font("Courier New", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label7.ForeColor = System.Drawing.SystemColors.MenuText;
      this.label7.Location = new System.Drawing.Point(272, 264);
      this.label7.Name = "label7";
      this.label7.Size = new System.Drawing.Size(251, 16);
      this.label7.TabIndex = 18;
      this.label7.Text = "Mob List";
      this.label7.TextAlign = System.Drawing.ContentAlignment.BottomCenter;
      // 
      // cmdAddMob
      // 
      this.cmdAddMob.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdAddMob.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdAddMob.Location = new System.Drawing.Point(272, 488);
      this.cmdAddMob.Name = "cmdAddMob";
      this.cmdAddMob.TabIndex = 13;
      this.cmdAddMob.Text = "Add";
      this.cmdAddMob.Click += new System.EventHandler(this.cmdAddMob_Click);
      // 
      // cmdEditMob
      // 
      this.cmdEditMob.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdEditMob.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdEditMob.Location = new System.Drawing.Point(360, 488);
      this.cmdEditMob.Name = "cmdEditMob";
      this.cmdEditMob.TabIndex = 14;
      this.cmdEditMob.Text = "Edit";
      this.cmdEditMob.Click += new System.EventHandler(this.cmdEditMob_Click);
      // 
      // cmdDeleteMob
      // 
      this.cmdDeleteMob.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdDeleteMob.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdDeleteMob.Location = new System.Drawing.Point(448, 488);
      this.cmdDeleteMob.Name = "cmdDeleteMob";
      this.cmdDeleteMob.TabIndex = 15;
      this.cmdDeleteMob.Text = "Delete";
      this.cmdDeleteMob.Click += new System.EventHandler(this.cmdDeleteMob_Click);
      // 
      // txtRepop
      // 
      this.txtRepop.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtRepop.Location = new System.Drawing.Point(80, 80);
      this.txtRepop.Name = "txtRepop";
      this.txtRepop.Size = new System.Drawing.Size(72, 20);
      this.txtRepop.TabIndex = 3;
      this.txtRepop.Text = "100";
      // 
      // label8
      // 
      this.label8.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label8.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label8.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label8.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label8.ForeColor = System.Drawing.SystemColors.ControlText;
      this.label8.Location = new System.Drawing.Point(8, 80);
      this.label8.Name = "label8";
      this.label8.Size = new System.Drawing.Size(64, 16);
      this.label8.TabIndex = 24;
      this.label8.Text = "Repop:";
      this.label8.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // cboScheme
      // 
      this.cboScheme.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
      this.cboScheme.Items.AddRange(new object[] {
                                                   "Normal",
                                                   "Red",
                                                   "Green",
                                                   "Blue",
                                                   "Yellow",
                                                   "Cyan",
                                                   "Magenta",
                                                   "White",
                                                   "Dark Red",
                                                   "Dark Green",
                                                   "Dark Blue",
                                                   "Dark Yellow",
                                                   "Dark Cyan",
                                                   "Purple"});
      this.cboScheme.Location = new System.Drawing.Point(80, 104);
      this.cboScheme.Name = "cboScheme";
      this.cboScheme.Size = new System.Drawing.Size(152, 21);
      this.cboScheme.TabIndex = 26;
      // 
      // label10
      // 
      this.label10.BackColor = System.Drawing.Color.AntiqueWhite;
      this.label10.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.label10.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.label10.Font = new System.Drawing.Font("Courier New", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label10.ForeColor = System.Drawing.SystemColors.ControlText;
      this.label10.Location = new System.Drawing.Point(8, 128);
      this.label10.Name = "label10";
      this.label10.Size = new System.Drawing.Size(64, 16);
      this.label10.TabIndex = 27;
      this.label10.Text = "Terrain:";
      this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // cboZoneTerrain
      // 
      this.cboZoneTerrain.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
      this.cboZoneTerrain.Items.AddRange(new object[] {
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
      this.cboZoneTerrain.Location = new System.Drawing.Point(80, 128);
      this.cboZoneTerrain.Name = "cboZoneTerrain";
      this.cboZoneTerrain.Size = new System.Drawing.Size(152, 21);
      this.cboZoneTerrain.TabIndex = 28;
      // 
      // cmdLinkZone
      // 
      this.cmdLinkZone.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdLinkZone.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdLinkZone.ForeColor = System.Drawing.SystemColors.InfoText;
      this.cmdLinkZone.Location = new System.Drawing.Point(80, 160);
      this.cmdLinkZone.Name = "cmdLinkZone";
      this.cmdLinkZone.Size = new System.Drawing.Size(112, 23);
      this.cmdLinkZone.TabIndex = 29;
      this.cmdLinkZone.Text = "Link Zone";
      this.cmdLinkZone.Click += new System.EventHandler(this.cmdLinkZone_Click);
      // 
      // cmdUpdateTerrain
      // 
      this.cmdUpdateTerrain.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdUpdateTerrain.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdUpdateTerrain.ForeColor = System.Drawing.SystemColors.InfoText;
      this.cmdUpdateTerrain.Location = new System.Drawing.Point(80, 192);
      this.cmdUpdateTerrain.Name = "cmdUpdateTerrain";
      this.cmdUpdateTerrain.Size = new System.Drawing.Size(112, 23);
      this.cmdUpdateTerrain.TabIndex = 30;
      this.cmdUpdateTerrain.Text = "Update Terrains";
      this.cmdUpdateTerrain.Click += new System.EventHandler(this.cmdUpdateTerrain_Click);
      // 
      // cmdDebugExits
      // 
      this.cmdDebugExits.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdDebugExits.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdDebugExits.ForeColor = System.Drawing.SystemColors.InfoText;
      this.cmdDebugExits.Location = new System.Drawing.Point(80, 224);
      this.cmdDebugExits.Name = "cmdDebugExits";
      this.cmdDebugExits.Size = new System.Drawing.Size(112, 23);
      this.cmdDebugExits.TabIndex = 31;
      this.cmdDebugExits.Text = "Debug Exits";
      this.cmdDebugExits.Click += new System.EventHandler(this.cmdDebugExits_Click);
      // 
      // frmMain
      // 
      this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
      this.BackColor = System.Drawing.SystemColors.HighlightText;
      this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
      this.ClientSize = new System.Drawing.Size(528, 519);
      this.Controls.Add(this.cmdDebugExits);
      this.Controls.Add(this.cmdUpdateTerrain);
      this.Controls.Add(this.cmdLinkZone);
      this.Controls.Add(this.cboZoneTerrain);
      this.Controls.Add(this.label10);
      this.Controls.Add(this.cboScheme);
      this.Controls.Add(this.label8);
      this.Controls.Add(this.txtRepop);
      this.Controls.Add(this.txtZoneName);
      this.Controls.Add(this.txtZoneID);
      this.Controls.Add(this.cmdDeleteMob);
      this.Controls.Add(this.cmdEditMob);
      this.Controls.Add(this.cmdAddMob);
      this.Controls.Add(this.label7);
      this.Controls.Add(this.lstMob);
      this.Controls.Add(this.cmdDeleteItem);
      this.Controls.Add(this.cmdEditItem);
      this.Controls.Add(this.cmdAddItem);
      this.Controls.Add(this.label6);
      this.Controls.Add(this.lstItem);
      this.Controls.Add(this.cmdDeleteRoom);
      this.Controls.Add(this.cmdEditRoom);
      this.Controls.Add(this.cmdAddRoom);
      this.Controls.Add(this.lstRoom);
      this.Controls.Add(this.label5);
      this.Controls.Add(this.label4);
      this.Controls.Add(this.label3);
      this.Controls.Add(this.label2);
      this.Controls.Add(this.label1);
      this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
      this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
      this.MaximizeBox = false;
      this.Menu = this.mainMenu;
      this.Name = "frmMain";
      this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
      this.Text = "Tempest Builder Beta";
      this.Closing += new System.ComponentModel.CancelEventHandler(this.frmMain_Closing);
      this.ResumeLayout(false);

    }
    #endregion

    [STAThread]
    static void Main()

    {
      Application.Run(new frmMain());
    }

    private void frmMain_Closing(object sender, System.ComponentModel.CancelEventArgs e)

    {
      if (main_changed)
      
      {      
        DialogResult dr = new DialogResult();

        dr = MessageBox.Show ("Save changes to the current zone?", "Save Changes",
          MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question);

        if (dr == DialogResult.Yes) { mnuSave_Click(sender, e); Application.Exit(); }
        if (dr == DialogResult.Cancel) e.Cancel = true;
        if (dr == DialogResult.No) Application.Exit();
      }
      
      else Application.Exit();
      
    }

    private void update_itemlist()

    {
      string temp;

      lstItem.Items.Clear();
      item_list.sort();

      for (int i=0; i<item_list.get_size(); i++)

      {
        temp  = item_list.get_item(i).id;
        temp  = "[" + temp.PadLeft(5, '0') + "] ";
        temp += item_list.get_item(i).name;
        lstItem.Items.Add(temp);
      }
    }

    private void update_roomlist()

    {
      string temp;

      lstRoom.Items.Clear();
      room_list.sort();

      for (int i=0; i<room_list.get_size(); i++)

      {
        temp  = room_list.get_room(i).get_id();
        temp  = "[" + temp.PadLeft(5, '0') + "] ";
        temp += room_list.get_room(i).get_name();
        lstRoom.Items.Add(temp);
      }
    }

    private void update_moblist()

    {
      string temp;

      lstMob.Items.Clear();
      mob_list.sort();

      for (int i=0; i<mob_list.get_size(); i++)

      {
        temp  = mob_list.get_mob(i).id;
        temp  = "[" + temp.PadLeft(5, '0') + "] ";
        temp += mob_list.get_mob(i).name;
        lstMob.Items.Add(temp);
      }
    }

    private void lstRoom_DoubleClick(object sender, System.EventArgs e)

    {
      cmdEditRoom_Click(sender, e);
    }

    private void lstItem_DoubleClick(object sender, System.EventArgs e)

    {
      cmdEditItem_Click(sender, e);
    }

    private void lstMob_DoubleClick(object sender, System.EventArgs e)

    {
      cmdEditMob_Click(sender, e);
    }

    private void cmdAddRoom_Click(object sender, System.EventArgs e)

    {
      main_changed = true;
      if (!check_zone_id()) {
        zone_id_error();
        return; }

      int current;
      int last = 0;
      bool found = false;
      room_editor = new frmRoomEditor(room_list, item_picker, mob_picker);
      room room_pointer = new room();

      if (room_list.get_size() == 0) {
        room_pointer.set_id(frmMain.get_zone_id() + "00");
        found = true; }

      else if (room_list.get_room(0).get_id() != frmMain.get_zone_id() + "00") {
        found = true;
        room_pointer.set_id(frmMain.get_zone_id() + "00"); }

      else for (int i=0; i<room_list.get_size(); i++)

      {
        current = Int32.Parse(room_list.get_room(i).get_id());
        if (i == 0) last = current - 1;

        if (current != last + 1)

        {
          found = true;
          last++;
          room_pointer.set_id(last.ToString());
          i = room_list.get_size();
        }

        last = current;
      }

      if (!found)

      {
        string str_id = room_list.get_room(room_list.get_size()-1).get_id();
        int int_id = Int32.Parse(str_id);
        int_id++;
        room_pointer.set_id(int_id.ToString());
      }

      room_editor.set_activeroom(room_pointer);
      room_editor.ShowDialog();

      update_roomlist();
    }

    private void cmdEditRoom_Click(object sender, System.EventArgs e)

    {
      main_changed = true;
      
      if (!check_zone_id()) {
        zone_id_error();
        return; }

      if (lstRoom.SelectedItem != null)

      {
        room_editor = new frmRoomEditor(room_list, item_picker, mob_picker);
        room_editor.set_activeroom(room_list.get_room(lstRoom.SelectedIndex));
        room_editor.ShowDialog();

        update_roomlist();
      }
    }

    private void cmdDeleteRoom_Click(object sender, System.EventArgs e)

    {
      main_changed = true;
      
      if (lstRoom.SelectedItem != null)
        if (MessageBox.Show ("Are you sure you want to delete this room?", "Room Delete",
          MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)

        {
          room_list.remove_room(room_list.get_room(lstRoom.SelectedIndex));
          update_roomlist();
        }
    }

    private void cmdAddItem_Click(object sender, System.EventArgs e)

    {
      main_changed = true;
      
      if (!check_zone_id()) {
        zone_id_error();
        return; }

      item_editor = new frmItemEditor(item_list);
      item_editor.new_item();
      item_editor.ShowDialog();

      update_itemlist();
    }

    private void cmdEditItem_Click(object sender, System.EventArgs e)

    {
      main_changed = true;
      
      if (!check_zone_id()) {
        zone_id_error();
        return; }

      if (lstItem.SelectedItem != null)

      {
        item_editor = new frmItemEditor(item_list);
        item_editor.edit_item(item_list.get_item(lstItem.SelectedIndex));
        item_editor.ShowDialog();

        update_itemlist();
      }
    }

    private void cmdDeleteItem_Click(object sender, System.EventArgs e)

    {
      main_changed = true;
      
      if (lstItem.SelectedItem != null)
      if (MessageBox.Show ("Are you sure you want to delete this item?", "Item Delete",
          MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)

      {
        item_list.remove_item(item_list.get_item(lstItem.SelectedIndex));

        update_itemlist();
      }
    }

    private void cmdAddMob_Click(object sender, System.EventArgs e)

    {
      main_changed = true;
      
      if (!check_zone_id()) {
        zone_id_error();
        return; }

      mob_editor  = new frmMobEditor(mob_list, item_picker);
      mob_editor.new_mob();
      mob_editor.ShowDialog();

      update_moblist();
    }

    private void cmdEditMob_Click(object sender, System.EventArgs e)

    {
      main_changed = true;
      
      if (!check_zone_id()) {
        zone_id_error();
        return; }

      if (lstMob.SelectedItem != null)

      {
        mob_editor  = new frmMobEditor(mob_list, item_picker);
        mob_editor.edit_mob(mob_list.get_mob(lstMob.SelectedIndex));
        mob_editor.ShowDialog();

        update_moblist();
      }
    }

    private void cmdDeleteMob_Click(object sender, System.EventArgs e)

    {
      main_changed = true;
      
      if (lstMob.SelectedItem != null)
        if (MessageBox.Show ("Are you sure you want to delete this mob?", "Mob Delete",
          MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)

        {
          mob_list.remove_mob(mob_list.get_mob(lstMob.SelectedIndex));
          update_moblist();
        }
    }

    private void clear()

    {
      item_path = "";
      mob_path = "";
      zone_path = "";
      txtZoneID.Text = "";
      txtZoneName.Text = "";
      txtRepop.Text = "";

      item_list.flush();
      mob_list.flush();
      room_list.flush();

      update_itemlist();
      update_moblist();
      update_roomlist();
      
      cboScheme.SelectedItem = (string)cboScheme.Items[12];
      cboZoneTerrain.SelectedItem = (string)cboZoneTerrain.Items[0];

      main_changed = false;
      saved_before = false;
    }

    private void mnuNew_Click(object sender, System.EventArgs e)

    {     
      if (main_changed)
      
      {
        DialogResult dr = new DialogResult();
        
        dr = MessageBox.Show ("Save changes to the current zone?", "Save Changes",
             MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question);

        if (dr == DialogResult.Yes) { mnuSave_Click(sender, e); clear(); }
        if (dr == DialogResult.No) clear();
      }
      
      else clear();
    }

    private void mnuExit_Click(object sender, System.EventArgs e)

    {
      this.Close();
    }

    private bool save_check()

    {
      if (txtZoneName.Text == "") {
        zone_name_error();
        return false; }

      if ((Utility.number(txtRepop.Text) == 0) || (txtRepop.Text == "")) {
        repop_error();
        return false; }

      int temp_repop = Int32.Parse(txtRepop.Text);

      if ((temp_repop < 0) || (temp_repop > 100)) {
        repop_error();
        return false; }

      return true;
    }

    private void save_all(string ipath, string mpath, string zpath)

    {
      StreamWriter zone_stream = File.CreateText(zpath + ".dat");
      StreamWriter item_stream = File.CreateText(ipath + ".itm");
      StreamWriter mob_stream  = File.CreateText(mpath + ".mob");

      zone_stream.WriteLine("Zone Name = " + get_color(cboScheme.Text) + txtZoneName.Text + "#N");
      zone_stream.WriteLine("Zone Number = " + txtZoneID.Text);
      zone_stream.WriteLine("Zone Size = " + room_list.get_size().ToString());
      zone_stream.WriteLine("Repop Rate = " + txtRepop.Text);
      zone_stream.WriteLine("Mobiles = " + room_list.get_popsize().ToString());
      zone_stream.WriteLine("Terrain = " + cboZoneTerrain.Text);
      zone_stream.WriteLine("Scheme = " + cboScheme.Text);
      zone_stream.WriteLine("");

      for (int i=0; i<room_list.get_size(); i++)
        zone_stream.WriteLine(room_list.get_room(i).create_record(get_color(cboScheme.Text)));

      zone_stream.Write("-End- ");
      zone_stream.Close();

      item_stream.WriteLine("Zone " + txtZoneID.Text);
      item_stream.WriteLine("Items = " + item_list.get_size().ToString());
      item_stream.WriteLine("");

      for (int i=0; i<item_list.get_size(); i++)
        item_stream.WriteLine(item_list.get_item(i).create_record());

      item_stream.Write("-End- ");
      item_stream.Close();

      mob_stream.WriteLine("Zone " + txtZoneID.Text);
      mob_stream.WriteLine("Mobile Types = " + mob_list.get_size().ToString());
      mob_stream.WriteLine("");

      for (int i=0; i<mob_list.get_size(); i++)
        mob_stream.WriteLine(mob_list.get_mob(i).create_record());

      mob_stream.Write("-End- ");
      mob_stream.Close();
    }

    private void mnuSaveAs_Click(object sender, System.EventArgs e)

    {
      FolderBrowserDialog FolderBrowser;

      FolderBrowser = new FolderBrowserDialog();
      FolderBrowser.Description = "Select a folder to save to:";

      if (FolderBrowser.ShowDialog() != DialogResult.OK)
        return;

      zone_path = FolderBrowser.SelectedPath + "\\zone" + txtZoneID.Text.PadLeft(3, '0');
      item_path = zone_path;
      mob_path = zone_path;

      save_all(zone_path, zone_path, zone_path);

      saved_before = true;
    }

    private void mnuSave_Click(object sender, System.EventArgs e)

    {
      if (!save_check())
        return;

      if (!saved_before) {
        mnuSaveAs_Click(sender, e);
        return; }

      save_all(item_path, mob_path, zone_path);
    }

    private void mnuOpen_Click(object sender, System.EventArgs e)

    {
      DialogResult dr = new DialogResult();
      
      if (main_changed)
      
      {
        dr = MessageBox.Show ("Save changes to the current zone?", "Save Changes",
          MessageBoxButtons.YesNoCancel, MessageBoxIcon.Question);

        if (dr == DialogResult.Yes) { mnuSave_Click(sender, e); clear(); }         
      }
      
      else dr = DialogResult.No;
      
      if (dr == DialogResult.No) clear();
      if (dr != DialogResult.Cancel)

      {
        OpenFileDialog FileDialog;
        FileDialog = new OpenFileDialog();
        FileDialog.InitialDirectory = "c:\\";
        FileDialog.Filter = "Zone files (*.dat)|*.dat";
        FileDialog.FilterIndex = 1;
        FileDialog.RestoreDirectory = true;

        if (FileDialog.ShowDialog() == DialogResult.OK)
          LoadZone(FileDialog.FileName);
      }
    }

    private void LoadZone(string path)

    {
      string input;
      string zfilename = "";
      string other_path, ipath, mpath;

      path = path.Substring(0, path.Length-4);
      zone_path = path;
      other_path = path;

      while ((int)other_path[other_path.Length-1] != 92) {
        zfilename = (other_path[other_path.Length-1].ToString()) + zfilename;
        other_path = other_path.Remove(other_path.Length-1, 1); }

      while ((int)other_path[other_path.Length-1] != 92)
        other_path = other_path.Remove(other_path.Length-1, 1);

      ipath = other_path + "..//items//" + zfilename;
      mpath = other_path + "..//mobiles//" + zfilename;

      FileStream zone_stream = new FileStream(path + ".dat", FileMode.Open);
      StreamReader zone_reader = new StreamReader(zone_stream);

      input = zone_reader.ReadLine();
      input = input.Substring(12, input.Length-12);
      input = input.Remove(0, 2);
      input = input.Remove(input.Length-2, 2);
      txtZoneName.Text = input;

      input = zone_reader.ReadLine();
      input = input.Substring(14, input.Length-14);
      txtZoneID.Text = input;

      input = zone_reader.ReadLine();
      input = zone_reader.ReadLine();
      input = input.Substring(13, input.Length-13);
      txtRepop.Text = input;
      
      input = zone_reader.ReadLine();
      input = zone_reader.ReadLine();
      input = input.Substring(10, input.Length-10);
      cboZoneTerrain.Text = input;
      
      input = zone_reader.ReadLine();
      input = input.Substring(9, input.Length-9);
      cboScheme.Text = input;

      if (File.Exists(path + ".itm"))

      {
        item_path = path;
        FileStream item_stream = new FileStream(path + ".itm", FileMode.Open);
        StreamReader item_reader = new StreamReader(item_stream);
        LoadItems(item_reader);
        item_reader.Close();
      }

      else if (File.Exists(ipath + ".itm"))

      {
        item_path = ipath;
        FileStream item_stream = new FileStream(ipath + ".itm", FileMode.Open);
        StreamReader item_reader = new StreamReader(item_stream);
        LoadItems(item_reader);
        item_reader.Close();
      }

      if (File.Exists(path + ".mob"))

      {
        mob_path = path;
        FileStream mob_stream = new FileStream(path + ".mob", FileMode.Open);
        StreamReader mob_reader = new StreamReader(mob_stream);
        LoadMobs(mob_reader);
        mob_reader.Close();
      }

      else if (File.Exists(mpath + ".mob"))

      {
        mob_path = mpath;
        FileStream mob_stream = new FileStream(mpath + ".mob", FileMode.Open);
        StreamReader mob_reader = new StreamReader(mob_stream);
        LoadMobs(mob_reader);
        mob_reader.Close();
      }

      LoadRooms(zone_reader);
      zone_reader.Close();

      update_roomlist();
      update_itemlist();
      update_moblist();

      saved_before = true;
    }

    private void LoadMobs(StreamReader sr)

    {
      mob temp;
      item temp2;
      string input, str;

      sr.ReadLine();
      input = sr.ReadLine();

      if (Int32.Parse(last(last(last(input)))) > 0)

      {
        sr.ReadLine();

        input = sr.ReadLine();

        temp = new mob();
        mob_list.add_mob(temp);
        temp.id = first(input);
        temp.name = last(input);
        temp.keywords = sr.ReadLine();
        temp.rdesc = sr.ReadLine();


        while (sr.Peek() != -1)

        {
          input = sr.ReadLine();

          if (first(input) == "[End]")

          {
            sr.ReadLine();
            input = sr.ReadLine();

            if (input != "-End- ")

            {
              temp = new mob();
              mob_list.add_mob(temp);
              temp.id = first(input);
              temp.name = last(input);
              temp.keywords = sr.ReadLine();
              temp.rdesc = sr.ReadLine();
            }
          }

          if (input == "BEGIN")

          {
            temp.ldesc = "";
            
            input = sr.ReadLine();
            while (input != "END") {
              temp.ldesc = temp.ldesc + input + " ";
              input = sr.ReadLine(); }
              
            temp.ldesc = Utility.clear_whitespace(temp.ldesc);
          }

          if (first(input) == "Level")
            temp.level = last(input);
          if (first(input) == "HP/MN")

          {
            input = last(last(input));
            temp.hitpoints = first(input);
            temp.mana = last(input);
          }

          if (first(input) == "Stats")

          {
            input = last(last(input));
            temp.strength = first(input);
            input = last(input);
            temp.dexerity = first(input);
            input = last(input);
            temp.constitution = first(input);
            input = last(input);
            temp.intelligence = first(input);
            input = last(input);
            temp.wisdom = first(input);
          }

          if (first(input) == "WStats")

          {
            input = last(last(input));
            temp.hitroll = first(input);
            input = last(input);
            temp.damroll = first(input);
            input = last(input);
            temp.armor_class = first(input);
            input = last(input);
            temp.magic_resistance = first(input);
          }

          if (first(input) == "Exp")

          {
            input = last(last(input));
            temp.experience = first(input);
          }

          if (first(input) == "Gold")

          {
            input = last(last(input));
            temp.gold = first(input);
          }

          if (first(input) == "Gender")

          {
            input = last(last(input));
            temp.gender = first(input);
          }

          if (first(input) == "Allign")

          {
            input = last(last(input));
            temp.allign = first(input);
          }

          if (first(input) == "Speed")

          {
            input = last(last(input));
            temp.speed = first(input);
          }

          if (first(input) == "FLAG")

          {
            input = last(input);

            if (input.Length > 9)
            if (input.Substring(0,8) == "MERCHANT") {
              temp.shop_name = input.Substring(9, input.Length-9);
              input = "MERCHANT"; }

            if (input == "MOBILE")            temp.mobile      =  true;
            else if (input == "BARD")         temp.bard        =  true;
            else if (input == "NPC")          temp.NPC         =  true;
            else if (input == "SKILLMASTER")  temp.skillmaster =  true;
            else if (input == "SPELLMASTER")  temp.spellmaster =  true;
            else if (input == "MERCHANT")     temp.merchant    =  true;
            else if (input == "BANKER")       temp.banker      =  true;
            else if (input == "AGGRO")        temp.aggro       =  true;
            else if (input == "UNDEAD")       temp.undead      =  true;
            else if (input == "SANCD")        temp.sancd       =  true;
            else if (input == "FLYING")       temp.flying      =  true;
            else if (input == "CLERGY")       temp.clergy      =  true;
            else if (input == "TEACHER")      temp.teacher     =  true;
            else if (input == "HERBALIST")    temp.herbalist   =  true;
          }

          if (first(input) == "ACT")

          {
            input = last(input);
            input = input.Remove(0, 1);
            str   = input.Substring(0, input.IndexOf("]"));
            input = input.Substring(input.IndexOf("]")+2, input.Length-input.IndexOf("]")-2);
            temp.actions.Add(first(input) + " " + str);
          }

          if (first(input) == "SKILL")

          {
            input = last(input);
            input = input.Remove(0, 1);
            str   = input.Substring(0, input.IndexOf("]"));
            input = input.Substring(input.IndexOf("]")+2, input.Length-input.IndexOf("]")-2);
            temp.skills.Add(first(input) + " " + str);
          }

          if (first(input) == "SPELL")

          {
            input = last(input);
            input = input.Remove(0, 1);
            str   = input.Substring(0, input.IndexOf("]"));
            input = input.Substring(input.IndexOf("]")+2, input.Length-input.IndexOf("]")-2);
            temp.spells.Add(first(input) + " " + str);
          }

          if (first(input) == "SELL")

          {
            input = last(input);

            while (first(input) != "")

            {
              temp2 = item_list.find_item(first(input));

              if (temp2 == null)

              {
                temp2 = new item();
                temp2.id = first(input);
                temp2.name = "Out of zone item.";
              }

              temp.sitems.add_item(temp2);
              input = last(input);
            }
          }

          if (first(input) == "ITEM")

          {
            input = last(input);
            temp2 = item_list.find_item(first(input));

            if (temp2 == null)

            {
              temp2 = new item();
              temp2.id = first(input);
              temp2.name = "Out of zone item.";
            }

            temp.litems.add_item(temp2);
            temp.load_items_freq.Add(last(input));
          }

          if (first(input) == "WEAR")

          {
            input = last(input);
            temp2 = item_list.find_item(first(input));

            if (temp2 == null)

            {
              temp2 = new item();
              temp2.id = first(input);
              temp2.name = "Out of zone item.";
            }

            temp.witems.add_item(temp2);
            temp.wear_items_freq.Add(last(input));
          }
        }
      }
    }

    private void LoadItems(StreamReader sr)

    {
      string input, str;
      item temp = null;

      sr.ReadLine();
      sr.ReadLine();
      sr.ReadLine();

      while (sr.Peek() != -1)

      {
        input = sr.ReadLine();

        if (first(input) == "ITEM:")

        {
          temp = new item();
          item_list.add_item(temp);
          temp.id = input.Substring(6, input.Length-6);
        }

        if (first(input) == "TYPE:")

        {
          string stype = last(input);
          if (stype == "GENERIC") temp.normal = true;
          else if (stype == "LIGHT") temp.light = true;
          else if (stype == "BLOCKER") temp.blocker = true;
          else if (stype == "CONTAINER") temp.container = true;
          else if (stype == "BOARD") temp.board = true;
          else if (stype == "SCROLL") temp.scroll = true;
          else if (stype == "EQUIPMENT") temp.equipment = true;
          else if (stype == "CONTAINEREQ") temp.eq_container = true;
          else if (stype == "DRINK") temp.consumable_drink = true;
          else if (stype == "DRINKCONTAINER") temp.refillable_drink = true;
          else if (stype == "FOUNTAIN") temp.fountain = true;
          else if (stype == "FOOD") temp.food = true;
        }

        if (first(input) == "LTICKS:") temp.lightticks = last(input);
        if (first(input) == "MAXITEMS:") temp.max_items = last(input);
        if (first(input) == "BOARDNAME:") temp.board_name = last(input);
        if (first(input) == "USES:") temp.uses = last(input);
        if (first(input) == "MAXUSES:") temp.uses = last(input);
        if (first(input) == "CSPELL:") temp.potion_spells.Add(last(input));
        if (first(input) == "INNATE:") temp.innate_spells.Add(last(input));
        if (first(input) == "CHARGED:") temp.charged_spells.Add(last(input));
        if (first(input) == "CHARGES:") temp.charges = last(input);
        if (first(input) == "NAME:") temp.name = last(input);
        if (first(input) == "LNAME:") temp.lname = last(input);
        if (first(input) == "GDESC:") temp.grounddesc = last(input);
        if (first(input) == "LEVEL:") temp.level = last(input);
        if (first(input) == "WORTH:") temp.worth = last(input);
        if (first(input) == "LTYPE:") temp.liquid = last(input);

        if (first(input) == "BTYPE:")

        {
          string stemp = last(input);
          
          temp.bdir = first(stemp);
          temp.btype = last(stemp);
        }

        if (first(input) == "DTYPE:")

        {
          string stemp = last(input);

          if (stemp == "STAB") temp.stab = true;
          else if (stemp == "SLASH") temp.slash = true;
          else if (stemp == "PIERCE") temp.pierce = true;
          else if (stemp == "BLUDGEON") temp.bludgeon = true;
          else if (stemp == "CHOP") temp.chop = true;
          else if (stemp == "WHIP") temp.whip = true;
          else if (stemp == "ARROW") temp.arrow = true;
          else if (stemp == "STAR") temp.star = true;
          else if (stemp == "FIST") temp.fist = true;
          else if (stemp == "THRUST") temp.thrust = true;
          else if (stemp == "GOUGE") temp.gouge = true;
          else if (stemp == "BLAST") temp.blast = true;
        }

        if (input == "BEGIN")

        {
          temp.lookatdesc = "";
          input = sr.ReadLine();
          while (input != "END") {
            temp.lookatdesc = temp.lookatdesc + input + " ";
            input = sr.ReadLine(); }
            
          temp.lookatdesc = Utility.clear_whitespace(temp.lookatdesc);
        }
        
        if (input == "BEGIN EXACT")
        
        {
          temp.exact_look = true;
          
          input = sr.ReadLine();
          while (input != "END") {
            temp.exact_description = temp.exact_description + input + "\r\n";
            input = sr.ReadLine(); }
            
          temp.exact_description = temp.exact_description.Substring(0,temp.exact_description.Length-2);
        }

        if (first(input) == "FLAG:")

        {
          string flag = last(input);
          if (flag == "PERISH") temp.perish = true;
          else if (flag == "INGREDIENT") temp.ingredient = true;
          else if (flag == "NO_GET") temp.no_get = true;
          else if (flag == "NO_SACRIFICE") temp.no_sacrifice = true;
          else if (flag == "NO_DROP") temp.no_drop = true;
          else if (flag == "GLOWING") temp.glowing = true;
          else if (flag == "HUMMING") temp.humming = true;
          else if (flag == "INVISIBLE") temp.invisible = true;
          else if (flag == "UNTOUCHABLE") temp.untouchable = true;
          else if (flag == "NO_RENT") temp.no_rent = true;
          else if (flag == "NEUTRAL") temp.neutral = true;
          else if (flag == "EVIL") temp.evil = true;
          else if (flag == "GOOD") temp.good = true;
          else if (flag == "BURNING") temp.burning = true;
          else if (flag == "FREEZING") temp.freezing = true;
          else if (flag == "SHOCKING") temp.shocking = true;
        }

        if (first(input) == "PLACE:")

        {
          input = last(input);

          while (true)

          {
            str = first(input);
            input = last(input);

            if (str == "Weapon")       temp.weapon = true;
            if (str == "Shield")       temp.shield = true;
            if (str == "Right_Hand")   temp.rhand = true;
            if (str == "Left_Hand")    temp.lhand = true;
            if (str == "Body")         temp.body = true;
            if (str == "Head")         temp.head = true;
            if (str == "Feet")         temp.feet = true;
            if (str == "Back")         temp.back = true;
            if (str == "Hands")        temp.hands = true;
            if (str == "Waist")        temp.waist = true;
            if (str == "Legs")         temp.legs = true;
            if (str == "Right_Ear")    temp.rear = true;
            if (str == "Left_Ear")     temp.lear = true;
            if (str == "Right_Wrist")  temp.rwrist = true;
            if (str == "Left_Wrist")   temp.lwrist = true;
            if (str == "Right_Finger") temp.rfinger = true;
            if (str == "Left_Finger")  temp.lfinger = true;
            if (str == "Neck")         temp.neck = true;
            if (str == "Both_Hands")   { temp.lhand = true; temp.rhand = true;     }
            if (str == "Both_Ears")    { temp.lear = true; temp.rear = true;       }
            if (str == "Both_Wrists")  { temp.lwrist = true; temp.rwrist = true;   }
            if (str == "Both_Fingers") { temp.lfinger = true; temp.rfinger = true; }

            if (input == "") break;
          }
        }

        if (first(input) == "CLASS:")

        {
          input = last(input);

          while (true)

          {
            str = first(input);
            input = last(input);

            if ((str == "All") || (str == "Wa")) temp.Wa = true;
            if ((str == "All") || (str == "Kn")) temp.Kn = true;
            if ((str == "All") || (str == "Br")) temp.Br = true;
            if ((str == "All") || (str == "Pa")) temp.Pa = true;
            if ((str == "All") || (str == "Dk")) temp.Dk = true;
            if ((str == "All") || (str == "Cr")) temp.Cr = true;
            if ((str == "All") || (str == "Ap")) temp.Ap = true;
            if ((str == "All") || (str == "Th")) temp.Th = true;
            if ((str == "All") || (str == "As")) temp.As = true;
            if ((str == "All") || (str == "Rg")) temp.Rg = true;
            if ((str == "All") || (str == "Ra")) temp.Ra = true;
            if ((str == "All") || (str == "Sb")) temp.Sb = true;
            if ((str == "All") || (str == "Me")) temp.Me = true;
            if ((str == "All") || (str == "Mc")) temp.Mc = true;
            if ((str == "All") || (str == "Cl")) temp.Cl = true;
            if ((str == "All") || (str == "Dr")) temp.Dr = true;
            if ((str == "All") || (str == "Mo")) temp.Mo = true;
            if ((str == "All") || (str == "Pr")) temp.Pr = true;
            if ((str == "All") || (str == "Dc")) temp.Dc = true;
            if ((str == "All") || (str == "Hl")) temp.Hl = true;
            if ((str == "All") || (str == "Al")) temp.Al = true;
            if ((str == "All") || (str == "Mg")) temp.Mg = true;
            if ((str == "All") || (str == "Wi")) temp.Wi = true;
            if ((str == "All") || (str == "Il")) temp.Il = true;
            if ((str == "All") || (str == "Wl")) temp.Wl = true;
            if ((str == "All") || (str == "Sc")) temp.Sc = true;
            if ((str == "All") || (str == "Sm")) temp.Sm = true;
            if ((str == "All") || (str == "Sh")) temp.Sh = true;

            if (input == "") break;
          }
        }

        if (first(input) == "SREQS:")

        {
          input = input.Remove(0, 7);

          temp.reqstatStr = input.Substring(0, input.IndexOf(" "));
          input = input.Remove(0, input.IndexOf(" ")+1);

          temp.reqstatDex = input.Substring(0, input.IndexOf(" "));
          input = input.Remove(0, input.IndexOf(" ")+1);

          temp.reqstatCon = input.Substring(0, input.IndexOf(" "));
          input = input.Remove(0, input.IndexOf(" ")+1);

          temp.reqstatInt = input.Substring(0, input.IndexOf(" "));
          input = input.Remove(0, input.IndexOf(" ")+1);

          temp.reqstatWis = input;
        }

        if (first(input) == "STATS:")

        {
          input = input.Remove(0, 7);

          temp.hit = input.Substring(0, input.IndexOf(" "));
          input = input.Remove(0, input.IndexOf(" ")+1);

          temp.dam = input.Substring(0, input.IndexOf(" "));
          input = input.Remove(0, input.IndexOf(" ")+1);

          temp.ac = input.Substring(0, input.IndexOf(" "));
          input = input.Remove(0, input.IndexOf(" ")+1);

          temp.res = input.Substring(0, input.IndexOf(" "));
          input = input.Remove(0, input.IndexOf(" ")+1);

          temp.hp = input.Substring(0, input.IndexOf(" "));
          input = input.Remove(0, input.IndexOf(" ")+1);

          temp.mana = input.Substring(0, input.IndexOf(" "));
          input = input.Remove(0, input.IndexOf(" ")+1);

          temp.move = input;
        }
      }
    }

    private void LoadRooms(StreamReader sr)

    {
      string input, str = "";
      room temp = null;
      bool exit = false;
      int tempint = 0;
      item temp2;

      while (sr.Peek() != -1)

      {
        input = sr.ReadLine();

        if (first(input) == "ROOM")

        {
          temp = new room();
          room_list.add_room(temp);
          temp.set_id(input.Substring(5, input.Length-5));
        }

        if (first(input) == "BEGIN")

        {
          bool is_exact = false;
          
          if (input != "BEGIN") is_exact = true;
          
          input = sr.ReadLine();

          if (input[0] == '#')

          {
            input = input.Remove(0, 2);
            input = input.Remove(input.Length-2, 2);
          }

          temp.set_name(input);

          input = sr.ReadLine();
          
          if (!is_exact)
          
          {
            while (first(input) != "END") {
              if (input[input.Length-1] != ' ') input = input + " ";
              temp.set_desc(temp.get_desc() + input);
              input = sr.ReadLine(); }

            temp.set_desc(Utility.clear_whitespace(temp.get_desc()));          
          }
          
          else
        
          {
            temp.exact = true;

            temp.exact_description = input + "\r\n";
            
            input = sr.ReadLine();
            
            while (input != "END") {
              temp.exact_description = temp.exact_description + input + "\r\n";
              input = sr.ReadLine(); }
              
            temp.exact_description = temp.exact_description.Substring(0,temp.exact_description.Length-2);
          }
        }

        if (first(input) == "LOOK")

        {
          input = input.Remove(0, 5);
          input = input.Remove(0, input.IndexOf(" ")+1);

          if ((input == "north") || (input == "south") ||
              (input == "east")  || (input == "west")  ||
              (input == "up")    || (input == "down"))

          {
            temp.get_exitkeys().Add(input);
            exit = true;
          }

          else

          {
            temp.get_keys().Add(input);
            exit = false;
          }

          input = sr.ReadLine();

          while (true) {
            input = sr.ReadLine();
            if (first(input) == "END") break;
            else str += input + " "; }

          if (exit) temp.get_exitdescs().Add(Utility.clear_whitespace(str));
          else temp.get_descs().Add(Utility.clear_whitespace(str));

          str = "";
        }

        if (first(input) == "NORTH")      temp.set_exit(0, last(input));
        else if (first(input) == "SOUTH") temp.set_exit(1, last(input));
        else if (first(input) == "EAST")  temp.set_exit(2, last(input));
        else if (first(input) == "WEST")  temp.set_exit(3, last(input));
        else if (first(input) == "UP")    temp.set_exit(4, last(input));
        else if (first(input) == "DOWN")  temp.set_exit(5, last(input));

        if (first(input) == "TERRAIN:")   temp.terrain = last(input);
        
        if (first(input) == "FLAG")

        {
          input = input.Remove(0, 5);

          if (input == "PKOK")           temp.set_pkok(true);
          else if (input == "LAWFULL")   temp.set_lawfull(true);
          else if (input == "NO_MAGIC")  temp.set_nomagic(true);
          else if (input == "INDOORS")   temp.set_indoors(true);
          else if (input == "NO_SUMMON") temp.set_nosummon(true);
          else if (input == "NO_QUIT")   temp.set_noquit(true);
          else if (input == "SILENT")    temp.set_silent(true);
          else if (input == "PRIVATE")   temp.set_private(true);
          else if (input == "NO_DROP")   temp.set_nodrop(true);
          else if (input == "NO_MOB")    temp.set_nomob(true);
          else if (input == "DONATION")  temp.set_donation(true);
          else if (input == "DARK")      temp.DARK = true;
          else if (input == "CHURCH")    temp.CHURCH = true;
          else if (input == "TUNNEL")    temp.TUNNEL = true;
          else if (input == "DAMAGE")    temp.DAMAGE = true;
          else if (input == "DEATHTRAP") temp.DEATHTRAP = true;
          else if (input == "UNDERWATER") temp.UNDERWATER = true;
          else if (input == "GODROOM")   temp.GODROOM = true;
          else if (input == "SHROUD")    temp.SHROUD = true;
          else if (input == "NO_TRACK")  temp.NO_TRACK = true;
          else if (input == "NO_SCOUT")  temp.NO_SCOUT = true;
          else if (input == "NO_DISPEL") temp.NO_DISPEL = true;
          else if (input == "NO_TELEPORT") temp.NO_TELEPORT = true;
          else if (input == "NO_FLEE")   temp.NO_FLEE = true;
          else if (input == "NO_SPELL")  temp.NO_SPELL = true;
          else if (input == "ANIMATE")   temp.ANIMATE = true;
          else if (input == "REGEN_HP")  temp.REGEN_HP = true;
          else if (input == "REGEN_MN")  temp.REGEN_MN = true;
        }

        if (first(input) == "ROOM_EVENT")

        {
          input   = input.Substring(12, input.Length-12);
          tempint = input.IndexOf("]");
          str     = input.Substring(0, tempint);
          input   = input.Remove(0, tempint+2);
          temp.get_actions().Add(input + " " + str);
        }

        if (first(input) == "LOAD_MOB")

        {
          if (mob_list.find_mob(last(input)) != null)
            temp.get_mobs().add_mob(mob_list.find_mob(last(input)));
        }

        if (first(input) == "LOAD_ITEM")

        {
          input = input.Remove(0, 10);
          temp2 = item_list.find_item(first(input));

          if (temp2 == null)

          {
            temp2 = new item();
            temp2.id = first(input);
            temp2.name = "Outside item";
          }

          temp.get_ilist().add_item(temp2);
        }
      }
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
    private string get_color(string str)

    {
      if (str == "Normal") return "#N";
      if (str == "Red") return "#r";
      if (str == "Green") return "#g";
      if (str == "Blue") return "#b";
      if (str == "Yellow") return "#y";
      if (str == "Cyan") return "#c";
      if (str == "Magenta") return "#m";
      if (str == "White") return "#n";
      if (str == "Dark Red") return "#R";
      if (str == "Dark Green") return "#G";
      if (str == "Dark Blue") return "#B";
      if (str == "Dark Yellow") return "#Y";
      if (str == "Dark Cyan") return "#C";
      if (str == "Purple") return "#M";

      return "#N";
    }

    private string convert_color(string str)

    {
      if (str == "#r") return "Red";
      else if (str == "#g") return "Green";
      else if (str == "#b") return "Blue";
      else if (str == "#y") return "Yellow";
      else if (str == "#c") return "Cyan";
      else if (str == "#m") return "Magenta";
      else if (str == "#n") return "White";
      else if (str == "#R") return "Dark Red";
      else if (str == "#G") return "Dark Green";
      else if (str == "#B") return "Dark Blue";
      else if (str == "#Y") return "Dark Yellow";
      else if (str == "#C") return "Dark Cyan";
      else if (str == "#M") return "Purple";
      else return "Normal";
    }

    private int visible_size(string s)

    {
      int size = s.Length;
      int vsize = s.Length;

      for (int i=size-1; i>0; i--)

      {
        if ((s[i-1] == '#') && (color_character(s[i])))
          vsize = vsize -2;
      }

      return vsize;
    }

    private string remove_colors(string str)

    {
      while (visible_size(str) < (int)str.Length)
        str = str.Remove(str.IndexOf("#"), 2);

      return str;
    }

    private bool color_character(char c)

    {
      if ((c == 'R') || (c == 'r') || (c == 'B') || (c == 'b')
        || (c == 'G') || (c == 'g') || (c == 'Y') || (c == 'y')
        || (c == 'M') || (c == 'm') || (c == 'C') || (c == 'c')
        || (c == 'N') || (c == 'n')) return true;

      return false;
    }

    private bool check_zone_id()
    {
      string temp = frmMain.get_zone_id();

      if (Utility.number(temp) == 1) return true;
      else return false;
    }

    private void zone_id_error()

    {
      MessageBox.Show("You must specify a valid Zone ID Number.", "Missing Zone ID.", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
    }

    private void zone_name_error()

    {
      MessageBox.Show("You must specify a Zone Name before saving.", "Missing Zone Name.", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
    }

    private void repop_error()

    {
      MessageBox.Show("Repop Rate must be a valid number between 0 and 100.", "Invalid Repop Rate.", MessageBoxButtons.OK, MessageBoxIcon.Exclamation);
    }

    private void cmdLinkZone_Click(object sender, System.EventArgs e)
    
    {
      main_changed = true;
      MessageBox.Show(room_list.link_rooms().ToString() + " room links created.");
    }

    private void cmdUpdateTerrain_Click(object sender, System.EventArgs e)
    
    {
      main_changed = true;
      if (MessageBox.Show("This will set ALL rooms to the zone terrain!  Are you sure?",
                          "Zone Terrain", MessageBoxButtons.OKCancel) == DialogResult.OK)
        room_list.default_terrain();
    }

    private void cmdDebugExits_Click(object sender, System.EventArgs e)
    
    {
      main_changed = true;
      MessageBox.Show(room_list.debug_rooms(), "Debug Report");
    }

    private void mnuCopy_Click(object sender, System.EventArgs e)
    
    {
      SendKeys.Send("^(C)");
    }

    private void mnuCut_Click(object sender, System.EventArgs e)
    
    {
      SendKeys.Send("^(X)");
    }

    private void mnuPaste_Click(object sender, System.EventArgs e)
    
    {
      SendKeys.Send("^(V)");
    }
    private void form_changed(object sender, System.EventArgs e)
    
    {
      main_changed = true;
    }

    private void mnuAbout_Click(object sender, EventArgs e)
    
    {
      frmHelp help_form = new frmHelp();
      help_form.set_output("Zone Form");
      help_form.ShowDialog();
    }

    private void menuItem3_Click(object sender, EventArgs e)
    
    {
      frmHelp help_form = new frmHelp();
      help_form.set_output("Room Form");
      help_form.ShowDialog();
    }

    private void menuItem4_Click(object sender, EventArgs e)
    
    {
      frmHelp help_form = new frmHelp();
      help_form.set_output("Mobile Form");
      help_form.ShowDialog();
    }

    private void menuItem5_Click(object sender, EventArgs e)
    
    {
      frmHelp help_form = new frmHelp();
      help_form.set_output("Item Form");
      help_form.ShowDialog();
    }

    private void menuItem7_Click(object sender, EventArgs e)
    
    {
      frmHelp help_form = new frmHelp();
      help_form.set_output("Balancing Mobile Stats");
      help_form.ShowDialog();     
    }

    private void menuItem8_Click(object sender, EventArgs e)
    
    {
      frmHelp help_form = new frmHelp();
      help_form.set_output("Balancing Item Stats");
      help_form.ShowDialog();      
    }

    private void menuItem9_Click(object sender, EventArgs e)
    
    {
      frmHelp help_form = new frmHelp();
      help_form.set_output("Balancing Item Prices");
      help_form.ShowDialog();      
    }

    private void menuItem11_Click(object sender, EventArgs e)
    
    {
      frmHelp help_form = new frmHelp();
      help_form.set_output("Standard Weapons List");
      help_form.ShowDialog();      
    }

    private void menuItem12_Click(object sender, EventArgs e)
    
    {
      frmHelp help_form = new frmHelp();
      help_form.set_output("Standard Armor List");
      help_form.ShowDialog();      
    }

    private void menuItem15_Click(object sender, System.EventArgs e)
    
    {
      frmHelp help_form = new frmHelp();
      help_form.set_output("Colors");
      help_form.ShowDialog();
    }

    private void menuItem16_Click(object sender, System.EventArgs e)
    
    {
      frmHelp help_form = new frmHelp();
      help_form.set_output("Frequencies");
      help_form.ShowDialog();
    }

    private void menuItem18_Click(object sender, System.EventArgs e)
    
    {
      frmHelp help_form = new frmHelp();
      help_form.set_output("Writing And Theme");
      help_form.ShowDialog();
    }

    private void menuItem14_Click(object sender, System.EventArgs e)

    {
      frmAbout about_form = new frmAbout();
      about_form.ShowDialog();
    }
  }
}
