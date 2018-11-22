class utilities {
  static char bool_to_char(boolean b) {
    if (b) {
      return '1';
    }
    return '0';
  }

  static String no_spaces(String s) {
    return s.replaceAll(" ", "");
  }
}
