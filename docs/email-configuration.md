# Email Configuration Guide

## Overview
This document provides guidance on configuring email settings for the application. Proper email configuration is essential for the application to send notifications to users.

## Configuration in application.properties

The email configuration is specified in the `application.properties` file. Here's an explanation of the key properties:

```properties
# Email server settings
spring.mail.host=smtp.office365.com    # SMTP server host
spring.mail.port=587                   # SMTP server port
spring.mail.username=your-email@example.com  # Email username/address
spring.mail.password=your-password     # Email password or App Password
spring.mail.properties.mail.smtp.auth=true  # Enable authentication
spring.mail.properties.mail.smtp.starttls.enable=true  # Enable STARTTLS
spring.mail.properties.mail.debug=false  # Set to true for debugging
```

## Authentication with 2FA Enabled

If your email account has Two-Factor Authentication (2FA) enabled, you'll need to use an App Password instead of your regular account password.

### For Outlook/Office365 Accounts:
1. Go to your Microsoft account > Security > Security dashboard
2. Under "Advanced security options", select "App passwords"
3. Create a new app password for "Mail" and use it in your configuration

### For Gmail Accounts:
1. Go to your Google Account > Security
2. Under "Signing in to Google", select "App passwords"
3. Generate a new app password for "Mail" and "Other (Custom name)"
4. Use the generated 16-character password in your configuration

## Troubleshooting

### Authentication Failures
If you see errors like:
```
Authentication failed when sending email. Check your email credentials in application.properties. For Gmail with 2FA, you need to use an App Password.
```

Or specifically for Microsoft/Outlook accounts:
```
Authentication unsuccessful, basic authentication is disabled.
```

Possible solutions:
1. Verify that your username and password are correct
2. If using 2FA, ensure you're using an App Password, not your regular password
3. Check that your email provider allows SMTP access for your account
4. For Microsoft/Outlook accounts:
   - Microsoft has disabled basic authentication for many accounts
   - Ensure you're using an App Password generated from your Microsoft account
   - If App Passwords don't work, you may need to use a different email provider or implement OAuth2 authentication

#### Microsoft's Basic Authentication Deprecation
Microsoft has deprecated basic authentication for Exchange Online and Office 365. This affects SMTP AUTH connections used by this application. Options to address this:

1. **Use App Passwords (Recommended for simplicity)**:
   - Enable 2FA on your Microsoft account
   - Generate an App Password specifically for this application
   - Use the App Password in your configuration

2. **Use a different email provider** that still supports basic authentication

3. **Implement OAuth2 authentication** (requires significant code changes)

### SSL/TLS Connection Issues
If you see errors related to SSL/TLS connections:
```
SSL/TLS connection failed. Check your mail server settings and SSL configuration.
```

Possible solutions:
1. Verify that the SMTP port is correct (usually 587 for STARTTLS, 465 for SSL)
2. Ensure that `spring.mail.properties.mail.smtp.starttls.enable` is set correctly
3. For debugging, set `spring.mail.properties.mail.debug=true`

## Email Configuration in Code

The application uses Spring's `JavaMailSender` for sending emails, configured in `MailConfig.java`. The configuration reads properties from `application.properties` and sets up the mail sender accordingly.

Key components:
- `JavaMailSenderImpl`: The implementation of `JavaMailSender` that handles sending emails
- `TemplateEngine`: Used for processing email templates
- `EmailNotificationService`: Service that uses the mail sender to send notifications

## Testing Email Configuration

To test your email configuration:
1. Set `spring.mail.properties.mail.debug=true` in `application.properties`
2. Restart the application
3. Trigger an action that sends an email
4. Check the logs for detailed information about the SMTP session

If everything is configured correctly, you should see successful authentication and message delivery in the logs.
