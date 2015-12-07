using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace Tempest_Builder

{
	public class frmExact : System.Windows.Forms.Form
	
	{
    private System.Windows.Forms.TextBox txtExact;
    private System.Windows.Forms.Button cmdCancel;
    private System.Windows.Forms.Button cmdSave;
		private System.ComponentModel.Container components = null;
		private bool changed = false;
		private string exact_text = "";
    private System.Windows.Forms.Label label1;
		private bool saved;

    public string get_exact()
    
    {
      return exact_text;
    }
    
    public bool get_saved()
    
    {
      return saved;
    }
    
    public void set_exact(string str)
    
    {
      exact_text = str;
    }
    
		public frmExact() // CONSTRUCTOR

		{
			InitializeComponent();
			
			for (int i=0; i<this.Controls.Count; i++)
		    if ((this.Controls[i] != cmdSave) && (this.Controls[i] != cmdCancel))
		      Controls[i].GotFocus += new EventHandler(Control_Changed);
		}

    private void Control_Changed(object sender, System.EventArgs e)
    
    {
      changed = true;
    }
    
		protected override void Dispose(bool disposing)
		
		{
			if(disposing)
			if(components != null)
			  components.Dispose();
			  
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
      System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(frmExact));
      this.txtExact = new System.Windows.Forms.TextBox();
      this.cmdCancel = new System.Windows.Forms.Button();
      this.cmdSave = new System.Windows.Forms.Button();
      this.label1 = new System.Windows.Forms.Label();
      this.SuspendLayout();
      // 
      // txtExact
      // 
      this.txtExact.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(64)), ((System.Byte)(0)), ((System.Byte)(64)));
      this.txtExact.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
      this.txtExact.Font = new System.Drawing.Font("Courier New", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.txtExact.ForeColor = System.Drawing.Color.FromArgb(((System.Byte)(224)), ((System.Byte)(224)), ((System.Byte)(224)));
      this.txtExact.Location = new System.Drawing.Point(8, 31);
      this.txtExact.Multiline = true;
      this.txtExact.Name = "txtExact";
      this.txtExact.Size = new System.Drawing.Size(896, 455);
      this.txtExact.TabIndex = 0;
      this.txtExact.Text = "";
      // 
      // cmdCancel
      // 
      this.cmdCancel.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdCancel.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdCancel.ForeColor = System.Drawing.SystemColors.InfoText;
      this.cmdCancel.Location = new System.Drawing.Point(475, 488);
      this.cmdCancel.Name = "cmdCancel";
      this.cmdCancel.TabIndex = 7;
      this.cmdCancel.Text = "Cancel";
      this.cmdCancel.Click += new System.EventHandler(this.cmdCancel_Click);
      // 
      // cmdSave
      // 
      this.cmdSave.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(219)), ((System.Byte)(219)), ((System.Byte)(219)));
      this.cmdSave.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
      this.cmdSave.ForeColor = System.Drawing.SystemColors.InfoText;
      this.cmdSave.Location = new System.Drawing.Point(363, 488);
      this.cmdSave.Name = "cmdSave";
      this.cmdSave.TabIndex = 6;
      this.cmdSave.Text = "Save";
      this.cmdSave.Click += new System.EventHandler(this.cmdSave_Click);
      // 
      // label1
      // 
      this.label1.BackColor = System.Drawing.Color.FromArgb(((System.Byte)(64)), ((System.Byte)(0)), ((System.Byte)(64)));
      this.label1.Font = new System.Drawing.Font("Courier New", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((System.Byte)(0)));
      this.label1.ForeColor = System.Drawing.Color.Lime;
      this.label1.Location = new System.Drawing.Point(8, 8);
      this.label1.Name = "label1";
      this.label1.Size = new System.Drawing.Size(896, 23);
      this.label1.TabIndex = 8;
      this.label1.Text = "|------------------------------------------------------------------------------|";
      // 
      // frmExact
      // 
      this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
      this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
      this.ClientSize = new System.Drawing.Size(912, 518);
      this.Controls.Add(this.label1);
      this.Controls.Add(this.cmdCancel);
      this.Controls.Add(this.cmdSave);
      this.Controls.Add(this.txtExact);
      this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
      this.Name = "frmExact";
      this.Text = "frmExact";
      this.ResumeLayout(false);

    }
		#endregion

    private void cmdCancel_Click(object sender, System.EventArgs e)
    
    {
      if (!changed) {
        saved = false;
        this.Hide(); }
      
      if (MessageBox.Show("Do you want to save your changes?",
          "Save Changes", MessageBoxButtons.YesNo) == DialogResult.Yes)
        cmdSave_Click(sender, e);
        
      else {
        saved = false;
        this.Hide(); }
    }

    private void cmdSave_Click(object sender, System.EventArgs e)
    
    {
      saved = true;
      exact_text = txtExact.Text;
      this.Hide();
    }
    
    protected override void OnPaint(PaintEventArgs e)
    
    {
      base.OnPaint (e);
      
      txtExact.Text = exact_text;
      cmdSave.Focus();
    }

	}
}
