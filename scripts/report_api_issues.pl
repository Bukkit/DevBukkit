#!/usr/bin/perl -w

use strict;
use File::Find;
use Config::Simple;

my @dirs = ( -1 != $#ARGV ) ? @ARGV : ( '.' );
my $checked = 0;
my $issues = 0;
my $renamed_ref = ();
my $removed_ref = ();
my $depricated_ref = ();

# for the convenience of &wanted calls, including -eval statements:
use vars qw/*name *dir *prune/;
*name   = *File::Find::name;
*dir    = *File::Find::dir;
*prune  = *File::Find::prune;

sub process
{
	if ( /^.*\.java\z/s )
	{
		$checked++;
		my $ret = open( my $fh, $_ );
		if ( ! $ret )
		{
			print STDERR "Failed to open '$name' ($!)\n";
			return;
		}
		my $text = do { local( $/ ) ; <$fh> } ;
		close( $fh );
		my @issues = check( 'Renamed', $renamed_ref, $text );
		push @issues, check( 'Removed', $removed_ref, $text );
		push @issues, check( 'Depricated', $depricated_ref, $text );

		if ( -1 != $#issues )
		{
			print "$name\n", @issues, "\n\n";
			$issues++;
		}
	}
}

sub check($$$)
{
	my ( $type, $find_ref, $text ) = @_;

	my @issues = ();
	foreach my $find ( keys %{$find_ref} )
	{
		push @issues, "$find -> $find_ref->{$find}\n" if ( $text =~ /$find/ );
	}

	if ( -1 != $#issues )
	{
		unshift @issues, "== $type ==\n";
	}

	return @issues;
}

{
	my $cfg = new Config::Simple('api_changes.cfg') or die Config::Simple->error();
	
	print join "\n", "Looking for ** Possible ** api issues in:-", @dirs;

	$renamed_ref = $cfg->get_block( 'renamed' );
	$removed_ref = $cfg->get_block( 'removed' );
	$depricated_ref = $cfg->get_block( 'depricated' );

	File::Find::find( { wanted => \&process }, @dirs );

	print "Checked $checked files, found $issues files with possible issues\n";
}

