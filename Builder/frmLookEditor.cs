using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace Tempest_Builder

{
  public class frmLookEditor : System.Windows.Forms.Form

  {
    private string keyword;
    private string desc;
    private bool saved;
    private int focus;

    #region GUI Variables
    private System.Windows.Forms.Label label1;
    private System.Windows.Forms.TextBox txtKeywords;
    private System.Windows.Forms.Label label2;
    private System.Windows.Forms.TextBox txtDesc;
    private System.Windows.Forms.Button cmdSave;
    private System.Windows.Forms.Button cmdCancel;
    private System.ComponentModel.Container components = null;
    #endregion

    public frmLookEditor()

    {
      InitializeComponent();
    }

    public void set_look(string key, string des, int foc)

    {
      keyword          = "";
      desc             = "";
      txtKeywords.Text = key;
      txtDesc.Text     = des;
      saved            = false;
      focus            = foc;
    }

    public bool get_saved()

    {
      return saved;
    }
    public void lock_key()

    {
      txtKeywords.ReadOnly = true;
    }

    public void unlock_key()

    {
      txtKeywords.ReadOnly = false;
    }

    public string get_keyword()

    {
      return keyword;
    }

    public string get_desc()

    {
      return desc;
    }
   

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
      System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(frmLookEditor));
      this.label1 = new System.Windows.Forms.Label();
      this.txtKeywords = new System.Windows.Forms.TextBox();
      this.label2 = new System.Windows.Forms.Label();
      this.txtDesc = new System.Windows.Forms.TextBox();
      this.cmdSave = new System.Windows.Forms.Button();
      this.cmdCancel = new System.Windows.Forms.Button();
      this.SuspendLayout();
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
      this.label1.TabIndex = 10;
      this.label1.Text = "Keys:";
      this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtKeywords
      // 
      this.txtKeywords.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtKeywords.Location = new System.Drawing.Point(64, 8);
      this.txtKeywords.Name = "txtKeywords";
      this.txtKeywords.Size = new System.Drawing.Size(240, 20);
      this.txtKeywords.TabIndex = 0;
      this.txtKeywords.Text = "";
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
      this.label2.TabIndex = 10;
      this.label2.Text = "Desc:";
      this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
      // 
      // txtDesc
      // 
      this.txtDesc.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtDesc.Location = new System.Drawing.Point(64, 32);
      this.txtDesc.Multiline = true;
      this.txtDesc.Name = "txtDesc";
      this.txtDesc.Size = new System.Drawing.Size(336, 104);
      this.txtDesc.TabIndex = 1;
      this.txtDesc.Text = "";
      // 
      // cmdSave
      // 
      this.cmdSave.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdSave.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdSave.Location = new System.Drawing.Point(128, 144);
      this.cmdSave.Name = "cmdSave";
      this.cmdSave.TabIndex = 2;
      this.cmdSave.Text = "Save";
      this.cmdSave.Click += new System.EventHandler(this.cmdSave_Click);
      // 
      // cmdCancel
      // 
      this.cmdCancel.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdCancel.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdCancel.Location = new System.Drawing.Point(216, 144);
      this.cmdCancel.Name = "cmdCancel";
      this.cmdCancel.TabIndex = 3;
      this.cmdCancel.Text = "Cancel";
      this.cmdCancel.Click += new System.EventHandler(this.cmdCancel_Click);
      // 
      // frmLookEditor
      // 
      this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
      this.BackColor = System.Drawing.SystemColors.Menu;
      this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
      this.ClientSize = new System.Drawing.Size(406, 174);
      this.ControlBox = false;
      this.Controls.Add(this.cmdCancel);
      this.Controls.Add(this.cmdSave);
      this.Controls.Add(this.txtDesc);
      this.Controls.Add(this.label2);
      this.Controls.Add(this.txtKeywords);
      this.Controls.Add(this.label1);
      this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
      this.Name = "frmLookEditor";
      this.ShowInTaskbar = false;
      this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
      this.Text = "Look Editor";
      this.ResumeLayout(false);

    }
		#endregion

    private void cmdSave_Click(object sender, System.EventArgs e)

    {
      bool valid = true;

      if (txtKeywords.Text == "")

      {
        MessageBox.Show ("You must enter at least one keyword.", "Missing keyword(s)",
          MessageBoxButtons.OK, MessageBoxIcon.Warning);
        valid = false;
      }

      else if (txtDesc.Text == "")

      {
        MessageBox.Show ("You must enter a description.", "Missing description",
          MessageBoxButtons.OK, MessageBoxIcon.Warning);
        valid = false;
      }

      if (valid)

      {
        keyword = txtKeywords.Text;
        desc = txtDesc.Text;
        saved = true;
        this.Hide();
      }
    }

    private void cmdCancel_Click(object sender, System.EventArgs e)

    {
      if (MessageBox.Show ("All changes will be lost. Are you sure you want to cancel?", "Cancel look",
        MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes) 
      {
        saved = false;
        this.Hide();
      }
    }

    protected override void Dispose( bool disposing )

    {
      if( disposing )
        if(components != null)
          components.Dispose();

      base.Dispose( disposing );
    }

    protected override void OnPaint(PaintEventArgs e)

    {
      if (focus == 1)
        txtDesc.Focus();
      else if (focus == 2)
        txtKeywords.Focus();
    }

	}
}