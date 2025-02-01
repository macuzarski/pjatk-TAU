import asyncio
from typing import Dict


class InsufficientFundsError(Exception):
    pass


class Account:
    def __init__(self, account_number: str, owner: str, initial_balance: float = 0.0):
        self.account_number = account_number
        self.owner = owner
        self.balance = initial_balance

    def deposit(self, amount: float):
        if amount <= 0:
            raise ValueError("Deposit amount must be positive.")
        self.balance += amount

    def withdraw(self, amount: float):
        if amount <= 0:
            raise ValueError("Withdrawal amount must be positive.")
        if self.balance < amount:
            raise InsufficientFundsError("Insufficient funds for withdrawal.")
        self.balance -= amount

    async def transfer(self, to_account: 'Account', amount: float):
        if amount <= 0:
            raise ValueError("Transfer amount must be positive.")
        if self.balance < amount:
            raise InsufficientFundsError("Insufficient funds for transfer.")

        await asyncio.sleep(0.1)  # Simulate network delay or processing time
        self.withdraw(amount)
        to_account.deposit(amount)


class Bank:
    def __init__(self):
        self.accounts: Dict[str, Account] = {}

    def create_account(self, account_number: str, owner: str, initial_balance: float = 0.0):
        if account_number in self.accounts:
            raise ValueError("Account number already exists.")
        self.accounts[account_number] = Account(account_number, owner, initial_balance)

    def get_account(self, account_number: str) -> Account:
        if account_number not in self.accounts:
            raise ValueError("Account not found.")
        return self.accounts[account_number]

    async def process_transaction(self, transaction_func):
        await transaction_func()
