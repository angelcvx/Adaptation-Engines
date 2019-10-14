# Feather Video app

This repository contains two possible scenarios in which adapting applications according to the user behavior make sense. First, by adapting the number of data/functionality requests and amount of information displayed on the screen according to the user profile. Examples of applications with this behavior are instant messaging apps (e.g., WhatsApp, Telegram), newspapers and weather apps, sport reports apps (e.g., LiveScore, DAZN), and market places (e.g., Play Store). The energy consumption of these kinds of applications can be reduced significantly if they only request and show the information which is relevant to the user. For instance, in the case of WhatsApp, it loads all the friends’ chats when it starts. Since the user usually only texts his/her most popular contacts, if it only displays and requests the information of these contacts, it can save energy without affecting the user experience. The second scenario for adapting user behavior consists in introducing a new functionality (or adapting an existing one) according to the known user profile (e.g., data compression before sending). Examples of applications with this behavior are social apps (e.g., Facebook, Instagram), e-mail clients (e.g., Gmail, Microsoft Outlook), videoconference apps (e.g., Skype, Hangouts), or Google Photos. Normally, these applications offer an interface where the user can select a video or photo from the gallery and choose between sending it as it is or compressing it. The adaptation in this case consists of selecting the compression algorithm taking into account the device context. Specifically, we consider the battery level, if the mobile phone is charging or not, and the available networks. The aim is to find the balance between the computational cost of the compression and the cost of sending the media file using the network on hand.

Code implementation by UMA.