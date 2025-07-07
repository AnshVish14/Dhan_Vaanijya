from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from typing import Optional
from vaderSentiment.vaderSentiment import SentimentIntensityAnalyzer

app = FastAPI()

# CORS settings
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Adjust in production for security
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

class StockData(BaseModel):
    stock: str
    past_price: float
    current_price: float
    news: str

@app.post("/predict")
async def analyze_stock(data: StockData):
    # Calculate percent price change
    change = data.current_price - data.past_price
    percent_change = (change / data.past_price) * 100 if data.past_price != 0 else 0

    # Price prediction based on percent change
    if percent_change >= 5:
        price_prediction = "Strong Buy"
    elif 1 <= percent_change < 5:
        price_prediction = "Buy"
    elif -1 < percent_change < 1:
        price_prediction = "Hold"
    elif -5 < percent_change <= -1:
        price_prediction = "Sell"
    else:
        price_prediction = "Strong Sell"

    # Sentiment analysis using VADER on the news text
    analyzer = SentimentIntensityAnalyzer()
    sentiment_scores = analyzer.polarity_scores(data.news)
    compound = sentiment_scores['compound']

    if compound >= 0.05:
        sentiment_label = "Positive"
    elif compound <= -0.05:
        sentiment_label = "Negative"
    else:
        sentiment_label = "Neutral"

    return {
        "stock": data.stock.upper(),
        "past_price": data.past_price,
        "current_price": data.current_price,
        "percent_change": round(percent_change, 2),
        "price_prediction": price_prediction,
        "news_sentiment": sentiment_label,
        "sentiment_scores": sentiment_scores
    }
