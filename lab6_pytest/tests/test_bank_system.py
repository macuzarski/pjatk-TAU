import pytest
import pytest_asyncio
from unittest.mock import AsyncMock, patch
from lab6_pytest.bank_system import Account, Bank, InsufficientFundsError

@pytest.fixture
def sample_bank():
    bank = Bank()
    bank.create_account("123", "Alice", 1000.0)
    bank.create_account("456", "Bob", 500.0)
    return bank

def test_account_deposit():
    account = Account("789", "Charlie", 100.0)
    account.deposit(50.0)
    assert account.balance == 150.0

def test_account_withdraw():
    account = Account("789", "Charlie", 100.0)
    account.withdraw(50.0)
    assert account.balance == 50.0

def test_account_withdraw_insufficient_funds():
    account = Account("789", "Charlie", 100.0)
    with pytest.raises(InsufficientFundsError):
        account.withdraw(150.0)

@pytest_asyncio.fixture
async def async_transfer_setup():
    acc1 = Account("101", "User1", 500.0)
    acc2 = Account("102", "User2", 200.0)
    return acc1, acc2

@pytest.mark.asyncio
async def test_account_transfer(async_transfer_setup):
    acc1, acc2 = async_transfer_setup
    await acc1.transfer(acc2, 100.0)
    assert acc1.balance == 400.0
    assert acc2.balance == 300.0

def test_bank_create_account(sample_bank):
    assert sample_bank.get_account("123").owner == "Alice"

def test_bank_get_account(sample_bank):
    account = sample_bank.get_account("456")
    assert account.owner == "Bob"

@pytest.mark.asyncio
async def test_bank_process_transaction(sample_bank):
    acc1 = sample_bank.get_account("123")
    acc2 = sample_bank.get_account("456")

    async def transaction():
        await acc1.transfer(acc2, 200.0)

    await sample_bank.process_transaction(transaction)
    assert acc1.balance == 800.0
    assert acc2.balance == 700.0

def test_account_deposit_negative_amount():
    account = Account("789", "Charlie", 100.0)
    with pytest.raises(ValueError):
        account.deposit(-50.0)

def test_bank_duplicate_account_creation(sample_bank):
    with pytest.raises(ValueError):
        sample_bank.create_account("123", "DuplicateOwner", 100.0)

@patch("lab6_pytest.bank_system.Account.transfer", new_callable=AsyncMock)
@pytest.mark.asyncio
async def test_mock_external_service(mock_transfer, sample_bank):
    acc1 = sample_bank.get_account("123")
    acc2 = sample_bank.get_account("456")

    mock_transfer.return_value = None  # Simulate successful external service

    await acc1.transfer(acc2, 100.0)
    mock_transfer.assert_called_once()
