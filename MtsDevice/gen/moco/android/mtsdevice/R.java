/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * aapt tool from the resource data it found.  It
 * should not be modified by hand.
 */

package moco.android.mtsdevice;

public final class R {
    public static final class attr {
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static final int buttonBarButtonStyle=0x7f010001;
        /** <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
         */
        public static final int buttonBarStyle=0x7f010000;
    }
    public static final class color {
        public static final int black_overlay=0x7f040000;
    }
    public static final class drawable {
        public static final int ic_launcher=0x7f020000;
    }
    public static final class id {
        public static final int TextView1=0x7f070014;
        public static final int addButton=0x7f070016;
        public static final int mental=0x7f07000d;
        public static final int mental_critical=0x7f070010;
        public static final int mental_stable=0x7f07000f;
        public static final int perfusion=0x7f07000a;
        public static final int perfusion_critical=0x7f07000c;
        public static final int perfusion_stable=0x7f07000b;
        public static final int respiration=0x7f070006;
        public static final int respiration_critical=0x7f070008;
        public static final int respiration_stable=0x7f070007;
        public static final int restart=0x7f070011;
        public static final int salvageText=0x7f070017;
        public static final int save=0x7f070012;
        public static final int tableRow1=0x7f070001;
        public static final int tableRow2=0x7f070005;
        public static final int tableRow3=0x7f070009;
        public static final int tag_color=0x7f070013;
        public static final int textView1=0x7f070000;
        public static final int textView2=0x7f070015;
        public static final int textView5=0x7f07000e;
        public static final int walk=0x7f070002;
        public static final int walk_no=0x7f070004;
        public static final int walk_yes=0x7f070003;
    }
    public static final class layout {
        public static final int triage_main=0x7f030000;
        public static final int triage_salvageinfo=0x7f030001;
        public static final int triage_salvageinfo_add=0x7f030002;
    }
    public static final class string {
        public static final int add=0x7f050014;
        public static final int app_name=0x7f050000;
        public static final int change=0x7f05000e;
        public static final int critical=0x7f05000b;
        public static final int dummy_button=0x7f050001;
        public static final int dummy_content=0x7f050002;
        public static final int error=0x7f05000c;
        public static final int error_category_missing=0x7f05000f;
        public static final int error_ok=0x7f050013;
        public static final int insert_info=0x7f050012;
        public static final int mental=0x7f050007;
        public static final int no=0x7f050009;
        public static final int perfusion=0x7f050006;
        public static final int question_restart=0x7f050011;
        public static final int respiration=0x7f050005;
        public static final int salvageinfo=0x7f050010;
        public static final int save=0x7f05000d;
        public static final int stable=0x7f05000a;
        public static final int triage_mode=0x7f050003;
        public static final int walk=0x7f050004;
        public static final int yes=0x7f050008;
    }
    public static final class style {
        /** 
        Base application theme, dependent on API level. This theme is replaced
        by AppBaseTheme from res/values-vXX/styles.xml on newer devices.

    

            Theme customizations available in newer API levels can go in
            res/values-vXX/styles.xml, while customizations related to
            backward-compatibility can go here.

        

        Base application theme for API 11+. This theme completely replaces
        AppBaseTheme from res/values/styles.xml on API 11+ devices.

    
 API 11 theme customizations can go here. 

        Base application theme for API 14+. This theme completely replaces
        AppBaseTheme from BOTH res/values/styles.xml and
        res/values-v11/styles.xml on API 14+ devices.
    
 API 14 theme customizations can go here. 
         */
        public static final int AppBaseTheme=0x7f060000;
        /**  Application theme. 
 All customizations that are NOT specific to a particular API-level can go here. 
         */
        public static final int AppTheme=0x7f060001;
        public static final int ButtonBar=0x7f060003;
        public static final int ButtonBarButton=0x7f060004;
        public static final int FullscreenActionBarStyle=0x7f060005;
        public static final int FullscreenTheme=0x7f060002;
    }
    public static final class styleable {
        /** 
         Declare custom theme attributes that allow changing which styles are
         used for button bars depending on the API level.
         ?android:attr/buttonBarStyle is new as of API 11 so this is
         necessary to support previous API levels.
    
           <p>Includes the following attributes:</p>
           <table>
           <colgroup align="left" />
           <colgroup align="left" />
           <tr><th>Attribute</th><th>Description</th></tr>
           <tr><td><code>{@link #ButtonBarContainerTheme_buttonBarButtonStyle moco.android.mtsdevice:buttonBarButtonStyle}</code></td><td></td></tr>
           <tr><td><code>{@link #ButtonBarContainerTheme_buttonBarStyle moco.android.mtsdevice:buttonBarStyle}</code></td><td></td></tr>
           </table>
           @see #ButtonBarContainerTheme_buttonBarButtonStyle
           @see #ButtonBarContainerTheme_buttonBarStyle
         */
        public static final int[] ButtonBarContainerTheme = {
            0x7f010000, 0x7f010001
        };
        /**
          <p>This symbol is the offset where the {@link moco.android.mtsdevice.R.attr#buttonBarButtonStyle}
          attribute's value can be found in the {@link #ButtonBarContainerTheme} array.


          <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
          @attr name android:buttonBarButtonStyle
        */
        public static final int ButtonBarContainerTheme_buttonBarButtonStyle = 1;
        /**
          <p>This symbol is the offset where the {@link moco.android.mtsdevice.R.attr#buttonBarStyle}
          attribute's value can be found in the {@link #ButtonBarContainerTheme} array.


          <p>Must be a reference to another resource, in the form "<code>@[+][<i>package</i>:]<i>type</i>:<i>name</i></code>"
or to a theme attribute in the form "<code>?[<i>package</i>:][<i>type</i>:]<i>name</i></code>".
          @attr name android:buttonBarStyle
        */
        public static final int ButtonBarContainerTheme_buttonBarStyle = 0;
    };
}
