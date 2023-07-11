import 'package:flutter/material.dart';
import 'package:phoenix/services/AuthService.dart';

class AuthProvider extends InheritedWidget {
  final AuthOktaService authService;

  AuthProvider({Key? key, required this.authService, required Widget child})
      : super(key: key, child: child);

  @override
  bool updateShouldNotify(InheritedWidget oldWidget) => true;

  static AuthProvider? of(BuildContext context) =>
      context.dependOnInheritedWidgetOfExactType<AuthProvider>();
}
