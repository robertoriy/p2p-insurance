// SPDX-License-Identifier: MIT

pragma solidity  0.8.19;

contract PeerToPeerInsurance {
    event LogEtherReceived(address sender, uint256 value);
    
    struct Participant {
        address payable account;
        uint256 value;
    }

    Participant[] private participants;
    uint256 private contributeValue;

    bool private contributionsCompleted;

    constructor(uint256 _contributeValue, address payable[] memory _accounts) {
        require(_accounts.length > 1, "At least two participants required for insurance");

        for (uint i = 0; i < _accounts.length; i++) {
            participants.push(Participant(_accounts[i], 0));
        }
        contributeValue = _contributeValue;
        contributionsCompleted = false;
    }

    function contribute() external payable {
        emit LogEtherReceived(msg.sender, msg.value);
        require(!contributionsCompleted,  "All participants has already contributed");
        require(msg.value > 0 , "Invalid contributeValue, must be greater than zero");
        require(msg.value == contributeValue, "Invalid contribution value, it must be equal to the initial one");


        for (uint i = 0; i < participants.length; i++) {
            if (msg.sender == participants[i].account) {
                if (participants[i].value > 0) {
                    revert("This participant has already contributed");
                }
                participants[i].value += msg.value;
                if (areAllContributionsComplete()) {
                    contributionsCompleted = true;
                }
                return;
            }
        }
        revert("Participant not found");
    }

    function reportInsuranceEvent() external {
        require(contributionsCompleted, "For insurance, you first need all participants to contribute to the contract!");
        require(isBalancesEquals(), "There is some error in balance of account!");

        for (uint i = 0; i < participants.length; i++) {
            if (msg.sender == participants[i].account) {
                participants[i].account.transfer(address(this).balance);
                resetParticipantValues();
                contributionsCompleted = false;
                return;
            }
        }
        revert("Participant not found");
    }

    function refund() external {
        require(contributionsCompleted, "For insurance, you first need all participants to contribute to the contract!");
        require(isBalancesEquals(), "There is some error in balance of account!");

        if (!isSenderParticipant(msg.sender)) {
            revert("Participant not found");
        }

        for (uint i = 0; i < participants.length; i++) {
            participants[i].account.transfer(participants[i].value);
            participants[i].value = 0;
        }

        contributionsCompleted = false;
    }

    function getContributeValue() external view returns(uint256) {
        return contributeValue;
    }

    function getContribution() external view returns(uint256) {
        for (uint i = 0; i < participants.length; i++) {
            if (msg.sender == participants[i].account) {
                return participants[i].value;
            }
        }
        revert("Participant not found");
    }

    function getContribution(address _sender) external view returns(uint256) {
        for (uint i = 0; i < participants.length; i++) {
            if (_sender == participants[i].account) {
                return participants[i].value;
            }
        }
        revert("Participant not found");
    }

    function getAllContributions() external view returns(uint256) {
        require(isBalancesEquals(), "There is some error in balance of account!");
        return address(this).balance;
    }

    function isInsuranceActive() external view returns (bool) {
        return contributionsCompleted;
    }

    function resetParticipantValues() internal {
        for (uint i = 0; i < participants.length; i++) {
            participants[i].value = 0;
        }
    }

    function isSenderParticipant(address _sender) internal view returns(bool) {
        for (uint i = 0; i < participants.length; i++) {
            if (_sender == participants[i].account) {
                return true;
            }
        }
        return false;
    }

    function isBalancesEquals() internal view returns(bool) {
        return address(this).balance == calculateTotalValue();
    }

    function calculateTotalValue() internal view returns(uint256) {
        uint totalValue = 0;
        for (uint i = 0; i < participants.length; i++) {
            totalValue += participants[i].value;
        }
        return totalValue;
    }

    function areAllContributionsComplete() internal view returns (bool) {
        for (uint i = 0; i < participants.length; i++) {
            if (participants[i].value == 0) {
                return false;
            }
        }
        return true;
    }
}