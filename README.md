# News Gateway App

**Uses**: Service, Broadcasts & Receivers, Drawer Layout, Fragments, ViewPager, Internet, APIs

The **News Gateway App** is an Android application that provides users with real-time news updates from a variety of sources and categories. Powered by the NewsAPI.org service, this app offers a seamless and interactive way to stay informed.

## Features

- **Dynamic News Sources**: Fetch and display a list of news sources dynamically from [NewsAPI.org](https://newsapi.org/).
- **Category Filtering**: Filter news sources by categories like business, technology, and sports.
- **Interactive Navigation**: Swipe through articles using a ViewPager for an intuitive reading experience.
- **Full Article Access**: Tap on any article title, image, or text to open the full article on the original news source’s website.
- **Professional UI**: Includes a custom launcher icon and dynamically generated content for enhanced usability.

## Technical Details

### Technologies Used
- **API Integration**: Uses NewsAPI.org endpoints to fetch data.
- **Android Components**:
  - **Drawer Layout** for displaying news sources.
  - **Fragments** for modular and dynamic content.
  - **ViewPager** for swipe-based navigation between articles.
  - **Services and Broadcast Receivers** for background data processing.
- **JSON Parsing**: Handles data from NewsAPI.org efficiently.

### API Endpoints
1. **Get News Sources**:
https://newsapi.org/v2/sources?language=en&country=us&category=<CATEGORY>&apiKey=<API_KEY>

2. **Get Top Headlines**:
https://newsapi.org/v2/top-headlines?sources=<SOURCE>&language=en&apiKey=<API_KEY>


## How to Use

1. **Select a Category**: Choose a news category from the options menu.
2. **Pick a News Source**: Browse through dynamically loaded sources in the navigation drawer.
3. **Swipe Through Articles**: Use left or right swipe gestures to view articles.
4. **Access Full Articles**: Tap on an article to open it on the original news provider’s website.

## Getting Started

### Prerequisites
- Android Studio installed on your system.
- A valid API key from [NewsAPI.org](https://newsapi.org/register).

### Installation
1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Add your API key to the configuration file.
4. Build and run the app on an emulator or Android device.






