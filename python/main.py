from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel

app = FastAPI()
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials = True,
    allow_methods=["*"],
    allow_headers=["*"],
)
class StockRequest(BaseModel):
    stock : str

@app.post("/predict")
async def predict(request: StockRequest):
    return {
        "stock": request.stock,
        "prediction": "Buy" if request.stock.upper() == "AAPL" else "Hold"
    }