package org.apache.olingo.jpa.metadata.core.edm.mapper.exception;

import java.util.Enumeration;
import java.util.Formatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class ODataJPAMessageTextBuffer implements ODataJPAMessageBufferRead {
  private static final String PATH_SEPERATOR = ".";

  public static Locale DEFAULT_LOCALE = Locale.ENGLISH;

  private String bundleName;
  private ResourceBundle bundle;
  private Locale locale = DEFAULT_LOCALE;
  private Enumeration<Locale> locales;

  public ODataJPAMessageTextBuffer(String bundleName) {
    super();
    this.bundleName = bundleName;
    getResourceBundle();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.olingo.jpa.metadata.core.edm.mapper.exception.ODataJPAMessageBufferRead#getText(java.lang.Object,
   * java.lang.String)
   */
  @Override
  public String getText(Object execption, String ID) {
    return bundle.getString(execption.getClass().getSimpleName() + PATH_SEPERATOR + ID);
  }

  @Override
  public String getText(Object execption, String ID, String... parameters) {
    String message = getText(execption, ID);
    StringBuilder builder = new StringBuilder();
    Formatter f = new Formatter(builder, locale);
    f.format(message, (Object[]) parameters);
    f.close();
    return builder.toString();
  }

  public void setLocale(Locale locale) {
    if (locale == null) {
      this.locale = DEFAULT_LOCALE;
      getResourceBundle();
    } else if (locale != this.locale) {
      this.locale = locale;
      getResourceBundle();
    }
  }

  public void setLocales(Enumeration<Locale> locales) {
    if (locales == null || locales.hasMoreElements() == false) {
      this.locale = DEFAULT_LOCALE;
      getResourceBundle();
    } else {
      while (locales.hasMoreElements()) {
        this.locale = locales.nextElement();
        getResourceBundle();
        if (bundle.getLocale().getLanguage() == this.locale.getLanguage()
            && bundle.getLocale().getCountry() == this.locale.getCountry())
          break;
      }
    }
  }

  String getBundleName() {
    return bundleName;
  }

  Locale getLocale() {
    return locale;
  }

  void setBundleName(String bundleName) {
    if (!this.bundleName.equals(bundleName)) {
      this.bundleName = bundleName;
      getResourceBundle();
    }
  }

  private void getResourceBundle() {
    bundle = ResourceBundle.getBundle(bundleName, locale);
  }
}
