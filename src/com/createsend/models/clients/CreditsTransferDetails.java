package com.createsend.models.clients;

public class CreditsTransferDetails {
	/**
	 * Represents the number of credits to transfer. This value may be either
	 * positive if you want to allocate credits from your account to the
	 * client, or negative if you want to deduct credits 
	 * from the client back into your account.
	 */
	public int Credits;
	/**
	 * If set to true, will allow the client to continue sending using your
	 * credits or payment details once they run out of credits, and if set to
	 * false, will prevent the client from using your credits to continue
	 * sending until you allocate more credits to them.
	 */
	public boolean CanUseMyCreditsWhenTheyRunOut;
}
